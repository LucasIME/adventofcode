defmodule AdventOfCode.Q20 do
  def broadcast_module() do
    %{
      :state => {},
      :process_pulse => fn state, pulse, _origin -> {state, pulse} end
    }
  end

  def flip_flop_module() do
    %{
      :state => :off,
      :process_pulse => fn state, pulse, _origin ->
        case pulse do
          :low ->
            case state do
              :off -> {:on, :high}
              :on -> {:off, :low}
            end

          :high ->
            {state, nil}
        end
      end
    }
  end

  def conjunction_module(inputs) do
    %{
      :state => inputs |> Enum.map(fn input -> {input, :low} end) |> Enum.into(%{}),
      :process_pulse => fn state, pulse, origin ->
        new_state = Map.put(state, origin, pulse)

        new_pulse =
          if new_state |> Map.values() |> Enum.all?(fn val -> val == :high end) do
            :low
          else
            :high
          end

        {new_state, new_pulse}
      end
    }
  end

  def to_module_state(raw_name, invert_graph) do
    case raw_name do
      "broadcaster" -> broadcast_module()
      "%" <> _ -> flip_flop_module()
      "&" <> name -> conjunction_module(Map.get(invert_graph, name))
    end
  end

  def to_module(raw_line, inverted_graph) do
    [raw_name, raw_neighbours] = String.split(raw_line, " -> ")
    neighbours = raw_neighbours |> String.split(", ")

    module = to_module_state(raw_name, inverted_graph)
    name = extract_name(raw_name)

    {name, %{:neighs => neighbours, :module => module}}
  end

  def create_modules(input, inverted_graph) do
    input
    |> Enum.map(&to_module(&1, inverted_graph))
    |> Enum.into(%{})
  end

  def extract_name(raw_name) do
    case raw_name do
      "broadcaster" -> raw_name
      "%" <> real_name -> real_name
      "&" <> real_name -> real_name
    end
  end

  def to_connection(raw_line) do
    [raw_name, raw_neighbours] = String.split(raw_line, " -> ")
    neighbours = raw_neighbours |> String.split(", ")

    name = extract_name(raw_name)
    {name, neighbours}
  end

  def to_graph(input) do
    input
    |> Enum.map(&to_connection/1)
    |> Enum.into(%{})
  end

  def invert_graph(graph) do
    graph
    |> Enum.flat_map(fn {key, values} -> Enum.map(values, fn val -> {val, key} end) end)
    |> Enum.reduce(%{}, fn {key, value}, acc ->
      Map.update(acc, key, [value], &[value | &1])
    end)
  end

  def process_pulses(
        modules,
        [],
        [],
        low_count,
        high_count,
        _src,
        _pulse_type,
        _target,
        has_reached_target?
      ) do
    {modules, low_count, high_count, has_reached_target?}
  end

  def process_pulses(
        modules,
        [],
        buffer_queue,
        low_count,
        high_count,
        src,
        pulse_type,
        target,
        has_reached_target?
      ) do
    process_pulses(
      modules,
      Enum.reverse(buffer_queue),
      [],
      low_count,
      high_count,
      src,
      pulse_type,
      target,
      has_reached_target?
    )
  end

  def process_pulses(
        modules,
        [cur | tail],
        buffer_queue,
        low_count,
        high_count,
        wanted_src,
        pulse_type,
        target,
        has_reached_target?
      ) do
    {src, pulse, destination} = cur

    did_hit? = src == wanted_src and pulse == pulse_type and destination == target
    new_reach = did_hit? or has_reached_target?

    {new_low, new_high} =
      case pulse do
        :low -> {low_count + 1, high_count}
        :high -> {low_count, high_count + 1}
      end

    target_module = modules[destination][:module]

    if target_module == nil do
      process_pulses(
        modules,
        tail,
        buffer_queue,
        new_low,
        new_high,
        wanted_src,
        pulse_type,
        target,
        new_reach
      )
    else
      {new_state, out_pulse} = target_module.process_pulse.(target_module[:state], pulse, src)

      new_modules = put_in(modules, [destination, :module, :state], new_state)

      new_destinations =
        if out_pulse == nil do
          []
        else
          modules[destination][:neighs]
          |> Enum.map(fn new_target -> {destination, out_pulse, new_target} end)
        end

      process_pulses(
        new_modules,
        tail,
        new_destinations ++ buffer_queue,
        new_low,
        new_high,
        wanted_src,
        pulse_type,
        target,
        new_reach
      )
    end
  end

  def press_button(modules, n, low \\ 0, high \\ 0)

  def press_button(modules, 0, low, high) do
    {modules, low, high}
  end

  def press_button(modules, n, low, high) do
    {next_state, low_count, high_count, _} =
      process_pulses(
        modules,
        [{"button", :low, "broadcaster"}],
        [],
        low,
        high,
        "any",
        :low,
        "any",
        false
      )

    press_button(next_state, n - 1, low_count, high_count)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input_list = input |> Enum.map(&String.trim/1) |> Enum.to_list()
    graph = to_graph(input_list)
    inverted_graph = graph |> invert_graph()

    modules = create_modules(input_list, inverted_graph)

    {_out, low, high} = press_button(modules, 1000)
    low * high
  end

  def press_until_target(modules, src, pulse_type, target, n \\ 1) do
    {new_modules, _low, _high, did_hit?} =
      process_pulses(
        modules,
        [{"button", :low, "broadcaster"}],
        [],
        0,
        0,
        src,
        pulse_type,
        target,
        false
      )

    if did_hit? do
      n
    else
      press_until_target(new_modules, src, pulse_type, target, n + 1)
    end
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    input_list = input |> Enum.map(&String.trim/1) |> Enum.to_list()
    graph = to_graph(input_list)
    inverted_graph = graph |> invert_graph()

    modules = create_modules(input_list, inverted_graph)

    target = "rx"
    [parent_needing_high_input] = inverted_graph[target]
    all_needing_high_input = inverted_graph[parent_needing_high_input]

    frequencies_needed =
      all_needing_high_input
      |> Enum.map(&press_until_target(modules, &1, :high, parent_needing_high_input))

    AdventOfCode.Utils.lcm(frequencies_needed)
  end
end
