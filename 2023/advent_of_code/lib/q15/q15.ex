defmodule AdventOfCode.Q15 do
  def hash_c(c, cur_val) do
    char_val = hd(String.to_charlist(c))
    ((cur_val + char_val) * 17) |> rem(256)
  end

  def hash([c | tail]) do
    Enum.reduce([c | tail], 0, &hash_c/2)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.flat_map(&String.split(&1, ","))
    |> Enum.map(&String.graphemes/1)
    |> Enum.map(&hash/1)
    |> Enum.sum()
  end

  def to_lens_and_label(entry) do
    [last | reversed_tail] = entry |> String.graphemes() |> Enum.reverse()

    cond do
      last == "-" ->
        {-1, Enum.reverse(reversed_tail) |> Enum.join()}

      true ->
        [label, lens] = String.split(entry, "=")
        {String.to_integer(lens), label}
    end
  end

  def remove_label(store, box, label) do
    box_content = elem(store, box)
    new_box_content = box_content |> Enum.filter(fn {cur_label, _val} -> cur_label != label end)
    put_elem(store, box, new_box_content)
  end

  def upsert_label(store, box, label, new_val) do
    box_content = elem(store, box)

    labels = box_content |> Enum.map(fn {label, _val} -> label end) |> MapSet.new()

    new_box_content =
      if MapSet.member?(labels, label) do
        box_content
        |> Enum.map(fn {cur_label, cur_val} ->
          if cur_label == label do
            {label, new_val}
          else
            {cur_label, cur_val}
          end
        end)
      else
        [{label, new_val} | box_content]
      end

    put_elem(store, box, new_box_content)
  end

  def process_instruction({lens, label}, store) do
    box = hash(label |> String.graphemes())

    case lens do
      -1 -> remove_label(store, box, label)
      lens_val -> upsert_label(store, box, label, lens_val)
    end
  end

  def get_store_focus_power(store) do
    store
    |> Enum.with_index(1)
    |> Enum.flat_map(fn {list, index} ->
      list
      |> Enum.with_index(1)
      |> Enum.map(fn {{_label, lens}, inner_index} ->
        {index, inner_index, lens}
      end)
    end)
    |> Enum.map(fn tuple -> tuple |> Tuple.to_list() |> Enum.reduce(1, &*/2) end)
    |> Enum.sum()
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    instructions =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.flat_map(&String.split(&1, ","))
      |> Enum.map(&to_lens_and_label/1)

    store = 0..255 |> Enum.to_list() |> Enum.map(fn _ -> [] end) |> List.to_tuple()

    final_store = Enum.reduce(instructions, store, &process_instruction/2)
    final_store = final_store |> Tuple.to_list() |> Enum.map(&Enum.reverse/1)

    get_store_focus_power(final_store)
  end
end
