defmodule AdventOfCode.Q25 do
  def to_graph(input) do
    input
    |> Enum.map(&String.split(&1, ": "))
    |> Enum.flat_map(fn [from, raw_to] ->
      to = raw_to |> String.split(" ")

      to |> Enum.flat_map(fn cur_to -> [{from, cur_to}, {cur_to, from}] end)
    end)
    |> Enum.reduce(%{}, fn {from, to}, acc ->
      Map.update(acc, from, MapSet.new([to]), &MapSet.union(&1, MapSet.new([to])))
    end)
  end

  def remove_edge(graph, {from, to}) do
    graph = Map.put(graph, from, MapSet.delete(Map.get(graph, from), to))
    graph = Map.put(graph, to, MapSet.delete(Map.get(graph, to), from))
    graph
  end

  def explore(graph, queue, visited \\ MapSet.new())

  def explore(_graph, [], visited) do
    visited
  end

  def explore(graph, [cur | tail], visited) do
    if MapSet.member?(visited, cur) do
      explore(graph, tail, visited)
    else
      new_visited = MapSet.put(visited, cur)
      neighs = graph[cur] |> MapSet.to_list()

      explore(graph, neighs ++ tail, new_visited)
    end
  end

  def shortest_path(graph, start, target) do
    bfs(graph, [start], MapSet.new([start]), %{start => []}, target)
  end

  defp bfs(_graph, [], _visited, _paths, _end_node) do
    []
  end

  defp bfs(graph, [current_node | queue], visited, paths, end_node) do
    if current_node == end_node do
      Map.get(paths, end_node)
    else
      neighbors = graph |> Map.get(current_node, []) |> Enum.reject(&MapSet.member?(visited, &1))
      new_visited = MapSet.union(visited, MapSet.new(neighbors))

      new_paths =
        Enum.reduce(neighbors, paths, fn neighbor, acc ->
          Map.put(acc, neighbor, [current_node | Map.get(paths, current_node)])
        end)

      bfs(graph, queue ++ neighbors, new_visited, new_paths, end_node)
    end
  end

  def remove_path(graph, []) do
    graph
  end

  def remove_path(graph, [_last]) do
    graph
  end

  def remove_path(graph, [node1, node2 | tail]) do
    graph = remove_edge(graph, {node1, node2})
    graph = remove_edge(graph, {node2, node1})
    remove_path(graph, [node2 | tail])
  end

  def find_disconnect_split2(graph) do
    nodes = graph |> Map.keys()
    node_pairs = for node1 <- nodes, node2 <- nodes, node1 != node2, do: {node1, node2}

    {new_graph, node1, node2} =
      node_pairs
      |> Enum.find_value(fn {node1, node2} ->
        # Trying to find two nodes that are on opposite sides on the final split.
        # Since there are exactly three edges connecting both splits, a path between nodes of different splits will always go through one of them.
        # Here we try to find the shorted path between two nodes and remove all these edges from the graph three times, and check if now the nodes and unconnected
        # If that's the case, they belong to different sides of the graph and we have our solution. Otherwise we need to continue to find a new pair of candidates.
        shortest = shortest_path(graph, node1, node2)
        graph = remove_path(graph, shortest)

        shortest2 = shortest_path(graph, node1, node2)
        graph = remove_path(graph, shortest2)

        shortest3 = shortest_path(graph, node1, node2)
        graph = remove_path(graph, shortest3)

        shortest4 = shortest_path(graph, node1, node2)

        if shortest != [] and shortest2 != [] and shortest3 != [] and shortest4 == [] do
          {graph, node1, node2}
        else
          false
        end
      end)

    [explore(new_graph, [node1]), explore(new_graph, [node2])]
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    graph =
      input
      |> Enum.map(&String.trim/1)
      |> to_graph()

    [set1, set2] = find_disconnect_split2(graph)

    MapSet.size(set1) * MapSet.size(set2)
  end
end
