import java.util.*;

public class Phase1 {
    public static void main(String[] args) {
        Scanner sc1 = new Scanner(System.in);
        String n1 = sc1.next();
        int n = 0;
        if (!Properties.isNumeric(n1)) {
            Properties.showInvalid();

        } else {
            n = Integer.parseInt(n1);
        }
        TreeMap<Integer, Graph> graphs = new TreeMap<>();
        for (int k = 0; k < n; k++) {
            String input = sc1.next();
            switch (input) {
                case "NEW_GRAPH": {
                    String id1 = "";
                    int id = 0;
                    try {
                        id1 = sc1.next();
                        id = Integer.parseInt(id1);
                    } catch (Exception e) {
                        System.out.println("INVALID_COMMAND");
                    }

                    if (!graphs.containsKey(id)) {
                        graphs.put(id, new Graph(id));
                    } else Properties.showInvalid();

                    break;
                }
                case "ADD_VERTEX": {
                    int[] parts = new int[2];
                    parts[0] = sc1.nextInt();
                    parts[1] = sc1.nextInt();
                    double w = sc1.nextDouble();
                    Vertex addingVertex = new Vertex(parts[1], w);
                    if (graphs.containsKey(parts[0]))
                        if (graphs.get(parts[0]) != null)
                            if (graphs.get(parts[0]).vertexes.containsKey(parts[1])) {
                                Properties.showInvalid();
                            } else graphs.get(parts[0]).vertexes.putIfAbsent(parts[1], addingVertex);
                        else Properties.showInvalid();

                    break;
                }
                case "ADD_EDGE": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();
                    a[2] = sc1.nextInt();
                    double w = sc1.nextDouble();
                    if (graphs.containsKey(a[0]))
                        if (graphs.get(a[0]) != null) {
                            graphs.get(a[0]).addEdge(a[1], a[2], w);
                        } else Properties.showInvalid();
                    break;
                }
                case "DEL_VERTEX": {

                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();
                    if (graphs.containsKey(a[0])) {
                        if (graphs.get(a[0]) != null) {
                            (graphs.get(a[0])).deleteVertex(a[1]);
                        }
                    } else Properties.showInvalid();
                    break;
                }
                case "DEL_EDGE": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();
                    a[2] = sc1.nextInt();

                    if (graphs.containsKey(a[0])) {
                        if (graphs.get(a[0]) != null) {
                            (graphs.get(a[0])).deleteEdge(a[1], a[2]);
                        }
                    } else Properties.showInvalid();
                    break;
                }
                case "EDIT_VERTEX": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();
                    double w = sc1.nextDouble();
                    if (graphs.containsKey(a[0]))
                        if (graphs.get(a[0]) != null) {
                            if (graphs.get(a[0]).vertexes.containsKey(a[1]))
                                graphs.get(a[0]).vertexes.get(a[1]).setWeight(w);
                            else Properties.showInvalid();
                        }

                    break;
                }
                case "EDIT_EDGE": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();
                    a[2] = sc1.nextInt();
                    double w = sc1.nextDouble();
                    if (graphs.containsKey(a[0]))
                        if (graphs.get(a[0]) != null) {
                            if (graphs.get(a[0]).vertexes.containsKey(a[1]))
                                graphs.get(a[0]).editEdge(a[1], a[2], w);
                            else Properties.showInvalid();
                        }


                    break;
                }
                case "SHOW_GRAPH": {
                    int id = sc1.nextInt();
                    StringBuilder ans = new StringBuilder();
                    if (graphs.containsKey(id))
                        if (graphs.get(id) != null) {
                            if (Objects.equals(graphs.get(id).getID(), id)) {
                                ans.append(graphs.get(id).getID()).append(" ").append(graphs.get(id).getVertexes().size()).append(" ").append(graphs.get(id).edges1.size()).append("\n");
                                for (Map.Entry<Integer, Vertex> entry : graphs.get(id).vertexes.entrySet()) {
                                    ans.append(id).append(" ").append(entry.getKey()).append(" ").append(String.format("%.6f", entry.getValue().getWeight())).append("\n");
                                }
                                for (Map.Entry<EdgeKey, Edge> entry : graphs.get(id).edges1.entrySet()) {
                                    ans.append(id).append(" ").append(entry.getValue().id_V1).append(" ").append(entry.getValue().id_V2).append(" ").append(String.format("%.6f", entry.getValue().weight)).append("\n");
                                }
                                System.out.print(ans);
                            } else Properties.showInvalid();
                        } else Properties.showInvalid();
                    else Properties.showInvalid();
                    break;
                }
                default:
                    Properties.showInvalid();
                    break;
            }
        }
    }


    public static class Graph {
        public TreeMap<Integer, Vertex> vertexes;
        private TreeMap<EdgeKey, Edge> edges1;

        Map<Integer, TreeSet<EdgeKey>> adjacencyMap = new HashMap<>();

        private TreeMap<Integer, Integer> id_edges;
        private TreeMap<Integer, Set<Integer>> id_edges3;

        private LinkedList<Pair> id_edges2;


        private int ID;

        public Graph(int ID) {
            vertexes = new TreeMap<>();
            edges1 = new TreeMap<>();
            id_edges = new TreeMap<>();
            id_edges2 = new LinkedList<>();
            id_edges3 = new TreeMap<>();
            this.ID = ID;
        }

        public static void addValue(Map<Integer, Set<Integer>> map, int key, int value) {
            map.putIfAbsent(key, new HashSet<>());
            map.get(key).add(value);
        }
        public static void removeValue(Map<Integer, Set<Integer>> map, int key, int value) {
            map.get(key).remove(value);
        }

        public void addEdge(int id1, int id2, double weight) {
            try {
                if (vertexes.containsKey(id1) && vertexes.containsKey(id2)) // چک میکنه که آیا دوتا راس هستن
                    if (id_edges3.containsKey(id1)) // چک میکنه که آیا راس اول قبلا یالی ازش رفته؟
                        if (!(id_edges3.get(id1).contains(id2))) {
                            check_id(id1, id2, weight);
                        } else Properties.showInvalid();
                    else {
                        check_id(id1, id2, weight);
                    }
                else Properties.showInvalid();
            } catch (Exception e) {
                Properties.showInvalid();
            }
        }

        private void check_id(int id1, int id2, double weight) {
            if (id_edges3.containsKey(id1))
                if (!(id_edges3.get(id1).contains(id2))) {
                    id_edges.put(id1, id2);
                    addValue(id_edges3,id1,id2);
                    EdgeKey key = new EdgeKey(id1, id2);
                    edges1.put(new EdgeKey(id1, id2), new Edge(id1, id2, weight));
                    adjacencyMap.computeIfAbsent(id1, k -> new TreeSet<>()).add(key);
                    adjacencyMap.computeIfAbsent(id2, k -> new TreeSet<>()).add(key);
                } else Properties.showInvalid();
            else {
                addValue(id_edges3,id1,id2);
                EdgeKey key = new EdgeKey(id1, id2);
                edges1.put(new EdgeKey(id1, id2), new Edge(id1, id2, weight));
                adjacencyMap.computeIfAbsent(id1, k -> new TreeSet<>()).add(key);
                adjacencyMap.computeIfAbsent(id2, k -> new TreeSet<>()).add(key);
            };
//            Vertex v1 = vertexes.get(id1);
//            Vertex v2 = vertexes.get(id2);
//            if (v1 != null && v2 != null) {
//                v1.addNeighbor(v2);
//                v2.addNeighbor(v1);
//            }
        }

        public double getMaxWeight() {
            Double output = 0.0;
            for (Vertex v : vertexes.values()) {
                if (v.getWeight() > output) {
                    output = v.getWeight();
                }
            }
            return output;
        }

        public int getSizeOfNodes() {
            return vertexes.size();
        }

        public int getSizeOfEdges() {
            int edgeCount = 0;
            for (Vertex v : vertexes.values()) {
                edgeCount += v.getNeighbors().size();
            }
            return edgeCount / 2;
        }

        public int getID() {
            return ID;
        }

        private static String generateKey(Integer id1, Integer id2) {
            return id1 + "-" + id2;
        }

        public TreeMap<Integer, Vertex> getVertexes() {
            return vertexes;
        }

        public Graph setVertexes(TreeMap<Integer, Vertex> vertexes) {
            this.vertexes = vertexes;
            return this;
        }


        public Graph setID(Integer ID) {
            this.ID = ID;
            return this;
        }

        public void deleteEdge(int startVertexId, int endVertexId) {
            EdgeKey e = new EdgeKey(startVertexId, endVertexId);

            if (vertexes.containsKey(startVertexId) && vertexes.containsKey(endVertexId)) {
                if (edges1.containsKey(e)) {
                    removeEdge1(edges1, adjacencyMap, new EdgeKey(startVertexId, endVertexId));
                    removeValue(id_edges3,startVertexId,endVertexId);
                } else Properties.showInvalid();
            } else Properties.showInvalid();


        }

        private static void removeVertexAndEdges(Map<EdgeKey, Edge> edgeMap, Map<Integer, TreeSet<EdgeKey>> adjacencyMap, int vertexToRemove) {
            TreeSet<EdgeKey> keysToRemove = adjacencyMap.get(vertexToRemove);
            if (keysToRemove != null) {
                for (EdgeKey key : keysToRemove) {
                    edgeMap.remove(key);
                }
            }
            adjacencyMap.remove(vertexToRemove);
        }

        private static void removeEdge1(Map<EdgeKey, Edge> edgeMap, Map<Integer, TreeSet<EdgeKey>> adjacencyMap, EdgeKey key) {
            Edge edge = edgeMap.remove(key);

            if (edge != null) {
                adjacencyMap.get(edge.getV1()).remove(key);
                adjacencyMap.get(edge.getV2()).remove(key);

                if (adjacencyMap.get(edge.getV1()).isEmpty()) {
                    adjacencyMap.remove(edge.getV1());
                }
                if (adjacencyMap.containsKey(edge.getV2()))
                    if (adjacencyMap.get(edge.getV2()).isEmpty()) {
                        adjacencyMap.remove(edge.getV2());
                    }
            } else Properties.showInvalid();
        }

        public void deleteVertex(Integer vertexId) {
            if (vertexes.containsKey(vertexId)) {
                removeVertexAndEdges(edges1, adjacencyMap, vertexId);
                vertexes.remove(vertexId);

            } else Properties.showInvalid();
        }

        public void editEdge(Integer startVertexId, Integer endVertexId, Double newWeight) {
            @SuppressWarnings("DuplicatedCode") EdgeKey e = new EdgeKey(startVertexId, endVertexId);
            if (!(!vertexes.containsKey(startVertexId) || !vertexes.containsKey(endVertexId))) {
                if (edges1.containsKey(e)) {
                    edges1.remove(e);
                    edges1.put(e, new Edge(startVertexId, endVertexId, newWeight));
                    return;
                } else {
                    Properties.showInvalid();
                    return;
                }
            }
            Properties.showInvalid();
        }


        public void printEdges() {


        }


        public TreeMap<EdgeKey, Edge> getEdges1() {
            return edges1;
        }

        public Graph setEdges1(TreeMap<EdgeKey, Edge> edges1) {
            this.edges1 = edges1;
            return this;
        }
    }

    public static class Properties {
        public static void showInvalid() {
            System.out.println("INVALID COMMAND");
        }

        public static boolean isNumeric(String str) {
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public static class Vertex {
        private final Integer id;
        private List<Vertex> neighbors;
        public TreeMap<Integer, Double> edges;
        private Double weight;

        public Vertex(Integer id, Double weight) {
            this.id = id;
            this.neighbors = new LinkedList<>();
            this.weight = weight;
        }

        public Integer getId() {
            return id;
        }

        public List<Vertex> getNeighbors() {
            return neighbors;
        }

        public void addNeighbor(Vertex neighbor) {
            neighbors.add(neighbor);
        }

        public Double getWeight() {
            return weight;
        }

        public Vertex setWeight(Double weight) {
            this.weight = weight;
            return this;
        }
    }

    public static class Edge implements Comparable<Edge> {
        private Integer id_V1, id_V2;
        private List<Edge> neighbors;
        private Double weight;

        public Edge(Integer v1, Integer v2, Double weight) {
            this.id_V1 = v1;
            this.id_V2 = v2;
            this.weight = weight;

        }

        public void addNeighbor(Edge neighbor) {
            neighbors.add(neighbor);
            weight++;
        }

        public Integer getV1() {
            return id_V1;
        }

        public Edge setV1(Integer v1) {
            this.id_V1 = v1;
            return this;
        }

        public Integer getV2() {
            return id_V2;
        }

        public Edge setV2(Integer v2) {
            this.id_V2 = v2;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Objects.equals(id_V1, edge.getV1()) &&
                    Objects.equals(id_V2, edge.getV2());
        }

        @Override
        public int hashCode() {
            return Objects.hash(id_V1, id_V2);
        }

        @Override
        public String toString() {
            return "Edge{" + "startVertexId=" + id_V1 + ", endVertexId=" + id_V2 + ", weight=" + weight + '}';
        }


        public List<Edge> getNeighbors() {
            return neighbors;
        }

        public Edge setNeighbors(List<Edge> neighbors) {
            this.neighbors = neighbors;
            return this;
        }

        public Double getWeight() {
            return weight;
        }

        public Edge setWeight(Double weight) {
            this.weight = weight;
            return this;
        }


        @Override
        public int compareTo(Edge comparing) {
            int startComparison = this.id_V1.compareTo(comparing.getV1());
            if (startComparison != 0) {
                return startComparison;
            }
            return this.id_V2.compareTo(comparing.getV2());
        }
    }


    public static class EdgeKey implements Comparable<EdgeKey> {
        private final int id1;
        private final int id2;

        public EdgeKey(int id1, int id2) {
            this.id1 = id1;
            this.id2 = id2;
        }

        public int getId1() {
            return id1;
        }

        public int getId2() {
            return id2;
        }

        @Override
        public int compareTo(EdgeKey other) {
            if (this.id1 != other.id1) {
                return Integer.compare(this.id1, other.id1); // مرتب‌سازی بر اساس id1
            }
            return Integer.compare(this.id2, other.id2); // مرتب‌سازی بر اساس id2
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            EdgeKey edgeKey = (EdgeKey) obj;
            return id1 == edgeKey.id1 && id2 == edgeKey.id2;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(id1) * 31 + Integer.hashCode(id2);
        }

        @Override
        public String toString() {
            return "(" + id1 + ", " + id2 + ")";
        }
    }
    public static class Pair {
        public final Integer first;
        public final Integer second;

        public Pair(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

}
