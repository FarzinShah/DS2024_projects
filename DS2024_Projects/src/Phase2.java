import java.io.Serializable;
import java.util.*;

public class Phase2 {
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
                case "CCC": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    System.out.println(graphs.get(a[0]).countConnectedComponents());
                    break;
                }
                case "MERGE_VERTEX": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();
                    show_graph(a[0], graphs);
                    break;
                }
                case "GET_DEGREE": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();
                    System.out.println(graphs.get(a[0]).calculateVertexDegrees(a[1]));
                    break;
                }
                case "IS_TREE": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    System.out.println(graphs.get(a[0]).isTree());
                    break;
                }
                case "Q": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();
                    a[2] = sc1.nextInt();
                    break;
                }
                case "T": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();
                    LinkedList<Graph> b = graphs.get(a[0]).generateAllSubgraphs();
                    LinkedList<Graph> c = graphs.get(a[0]).generateAllSubgraphs();
//                    for (int i = 0; i < b.size(); i++) {
//                        calculateContractionCost(b.get(i),graphs.get(a[0]));
//
//                    }

                    System.out.println(b.size());
//                    for (int i = 0; i < b.size(); i++) {
//                        for (int j = 0; j < c.size(); j++) {
//                            calculateContractionCost()
//
//                        }
//
//                    }


                    break;
                }
                case "GRAPH_DISTANCE": {
                    int[] a = new int[5];
                    a[0] = sc1.nextInt();
                    a[1] = sc1.nextInt();

                    if (graphs.containsKey(a[0]) && graphs.containsKey(a[1])) {
                        Graph g1 = graphs.get(a[0]);
                        Graph g2 = graphs.get(a[1]);
                        Graph g1C = g1.deepCopy();
                        Graph g2C = g2.deepCopy();
                        Graph g1CC = g1.deepCopy();
                        Graph g2CC = g2.deepCopy();
                        //g1 , g2 should be change;
                        int cccG1 = g1.countConnectedComponents(), cccG2 = g2.countConnectedComponents();
                        if (cccG1 != cccG2) { // todo: حالت کلی اول: چک بکنم مولفه همبندیا برابرن
                            System.out.println("inf");
                            break;
                        }
                        //todo: حالت کلی دوم: دوتا گراف رو بدم لیست بگیرم بعد مینیممشون رو دریابم
                        boolean isIdeaTrue = false; //todo: جداً؟؟؟
                        //todo: به ازای مولفه های همبندی اینکار رو بکنم. +-+-+-+-+-+-+

                        if (!isIdeaTrue) {

                            LinkedList<Graph> l1 = new LinkedList<>();
                            LinkedList<Graph> l2 = new LinkedList<>();
                            Queue<Graph> q1 = new LinkedList<>();
                            q1.add(g1CC);
                            Queue<Graph> q2 = new LinkedList<>();
                            q2.add(g2CC);
                            while (!q1.isEmpty()) {
                                Graph z = q1.remove();
                                LinkedList<Graph> g = new LinkedList<>();
                                g.add(z);
//                               show_graph2(g);
                                l1.add(z);
                                q1.addAll(result3(z));
                            }
                            while (!q2.isEmpty()) {
                                Graph z2 = q2.remove();
                                l2.add(z2);
                                q2.addAll(result3(z2));
                            }

                            double resBFS = getMinCost(l1, l2);
                            if (resBFS == Double.MAX_VALUE) {
                                System.out.println("inf");
                                break;
                            } else {
                                System.out.printf("%.6f%n", resBFS);
                            }
                            break;
                        }
                    }
                    break;
                }
                case "SHOW_GRAPH": {
                    int id = sc1.nextInt();
                    show_graph(id, graphs);
                    break;
                }
                default:
                    Properties.showInvalid();
                    break;
            }
        }


    }

    public static boolean isAllVertexesIsolate(Graph g) {
        return g.edges1.size() == 0;
    }

    public static LinkedList<Graph> result3(Graph g1) {
        Graph g2 = g1.deepCopy();
        LinkedList<Graph> res = new LinkedList<>();
        for (Map.Entry<Integer, Vertex> entry : new HashMap<>(g1.vertexes).entrySet()) {
            if (getVertexDegree(g1, entry.getValue().id) >= 1) { //todo: is it correct? absolutely no!
                Graph contracted = g1.contractVertex3(entry.getValue().id);
                res.add(contracted);
            }

        }
        for (Map.Entry<EdgeKey, Edge> entry : new HashMap<>(g2.edges1).entrySet()) {
//            System.out.println("contractEdge: id1: " + entry.getValue().id_V1 + " - id2: " + entry.getValue().id_V2);
            Graph contracted = g2.contractEdge3(entry.getValue().id_V1, entry.getValue().id_V2);
            if (contracted != null)
                res.add(contracted);
        }

        return res;
    }

    public static int getVertexDegree(Graph graph, int vertexId) {
        if (!graph.vertexes.containsKey(vertexId)) {
//            System.err.println("Vertex " + vertexId + " not found in the graph.");
            return -1;
        }

        int degree = 0;

        for (Edge edge : graph.edges1.values()) {
            if (edge.getV1() == vertexId || edge.getV2() == vertexId) {
                degree++;
            }
        }

        return degree;
    }


    public static Double getMinCost(LinkedList<Graph> l1, LinkedList<Graph> l2) {
        double result = Double.MAX_VALUE;
        for (Graph graph : l1) {
            for (Graph value : l2) {
                if (Isomorphism.areIsomorphic(graph, value)) {
                    double currentCost = Double.parseDouble(Isomorphism.distance(Isomorphism.areIsomorphic(graph, value), graph, value, false));
                    if (result > currentCost + graph.cost + value.cost) {
//                        System.out.println("current cost: " + currentCost + "graph cost: " + graph.cost + "value cost: " + value.cost);
                        result = currentCost + graph.cost + value.cost;
//                        if(result == 3953.930000) {
//                            System.out.println("cost1 : " + graph.cost + " - cost2 : " + value.cost);
//                            LinkedList<Graph> A = new LinkedList();
//                            A.add(graph);
//                            show_graph2(A);
//                            A.clear();
//                            A.add(value);
//                            show_graph2(A);
//                            Double.parseDouble(Isomorphism.distance(Isomorphism.areIsomorphic(graph, value), graph, value, true));
//                        }
                    }
                }
            }
        }
        return result;
    }



    public static void show_graph(int id, TreeMap<Integer, Graph> graphs) {
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
    }

    public static void show_graph2(LinkedList<Graph> graphs) {
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < graphs.size(); i++) {
            ans.append(graphs.get(i).getID()).append(" ").append(graphs.get(i).getVertexes().size()).append(" ").append(graphs.get(i).edges1.size()).append("\n");
            for (Map.Entry<Integer, Vertex> entry : graphs.get(i).vertexes.entrySet()) {
                System.out.println("getWeight() : " + entry.getValue().getWeight());
                ans.append(graphs.get(i).ID).append(" ").append(entry.getKey()).append(" ").append(String.format("%.6f", entry.getValue().getWeight())).append("\n");
            }
            for (Map.Entry<EdgeKey, Edge> entry : graphs.get(i).edges1.entrySet()) {
                ans.append(graphs.get(i).ID).append(" ").append(entry.getValue().id_V1).append(" ").append(entry.getValue().id_V2).append(" ").append(String.format("%.6f", entry.getValue().weight)).append("\n");
            }
            System.out.print(ans);
            ans = new StringBuilder();
        }
        System.out.println(graphs.size());
    }

    public static int findNewId(Graph g, int oldId) {
        int index = 0;
        for (int key : g.vertexes.keySet()) {
            if (key == oldId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static class Graph implements Serializable, Cloneable {
        public TreeMap<Integer, Vertex> vertexes;
        public TreeMap<EdgeKey, Edge> edges1;
        public LinkedList<EdgePair> edgePairs;
        public LinkedList<String> edgePairs1;

        Map<Integer, TreeSet<EdgeKey>> adjacencyMap = new HashMap<>();

        private final TreeMap<Integer, Integer> id_edges;
        private final TreeMap<Integer, Set<Integer>> id_edges3;

        private final LinkedList<Pair> id_edges2;

        private double cost;
        private int ID;

        public Graph(int ID) {
            vertexes = new TreeMap<>();
            edges1 = new TreeMap<>();
            id_edges = new TreeMap<>();
            edgePairs = new LinkedList<>();
            edgePairs1 = new LinkedList<>();
            id_edges2 = new LinkedList<>();
            id_edges3 = new TreeMap<>();
            this.ID = ID;
            this.cost = 0.0;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }


        public LinkedList<Graph> generateAllSubgraphs() {
            LinkedList<Graph> subgraphs = new LinkedList<>();
            int id = 1;
            List<Set<Integer>> vertexSubsets = generateVertexSubsets(this.vertexes.keySet());
            for (Set<Integer> vertexSubset : vertexSubsets) {
                Graph subgraph = extractSubgraph(this, vertexSubset, id);
                subgraphs.add(subgraph);
                id++;
            }

            return subgraphs;
        }

        private List<Set<Integer>> generateVertexSubsets(Set<Integer> vertices) {
            List<Set<Integer>> subsets = new ArrayList<>();
            List<Integer> vertexList = new ArrayList<>(vertices);
            int totalSubsets = 1 << vertexList.size();
            for (int mask = 0; mask < totalSubsets; mask++) {
                Set<Integer> subset = new HashSet<>();
                for (int i = 0; i < vertexList.size(); i++) {
                    if ((mask & (1 << i)) != 0) {
                        subset.add(vertexList.get(i));
                    }
                }
                subsets.add(subset);
            }

            return subsets;
        }


        private Graph extractSubgraph(Graph graph, Set<Integer> vertexSubset, int id) {
            Graph subgraph = new Graph(id);

            for (Integer vertex : vertexSubset) {
                if (graph.vertexes.containsKey(vertex)) {
                    subgraph.vertexes.put(vertex, graph.vertexes.get(vertex));
                }
            }

            for (Edge edge : graph.edges1.values()) {
                if (vertexSubset.contains(edge.getV1()) && vertexSubset.contains(edge.getV2())) {
                    subgraph.edges1.put(new EdgeKey(edge.getV1(), edge.getV2()), edge);
                }
            }

            return subgraph;
        }


        public boolean isFulled() {
            return edges1.size() == (vertexes.size() * (vertexes.size() - 1) / 2);
        }

        private boolean isTree() {
            return this.edges1.size() == this.vertexes.size() - 1 && this.countConnectedComponents() == 1;
        }


        public Graph contractVertex3(int vertexId) {
            Graph original = this.deepCopy();
            Vertex vertexToContract = original.vertexes.get(vertexId);
            if (vertexToContract == null) {
//                System.out.println("******************************************************************");
                return original;
            }
            double totalWeight = vertexToContract.getWeight();
//            System.out.println("totalWeight:" + totalWeight);
            double edgeWeights = 0.0;
            double neighborWeights = 0.0;

            List<Integer> neighbors = new ArrayList<>();
            for (Edge edge : original.edges1.values()) {
                if (edge.getV1() == vertexId) {
                    neighbors.add(edge.getV2());
                    edgeWeights += edge.getWeight();
                } else if (edge.getV2() == vertexId) {
                    neighbors.add(edge.getV1());
                    edgeWeights += edge.getWeight();
                }
            }


            for (Integer neighbor : neighbors) {
                Vertex neighborVertex = original.vertexes.get(neighbor);
                if (neighborVertex != null) {
                    neighborWeights += neighborVertex.getWeight();
                }
            }
            double newWeight = totalWeight + neighborWeights + edgeWeights;
            for (Integer neighbor : neighbors) {
                for (Map.Entry<EdgeKey, Edge> entry : new HashMap<>(original.edges1).entrySet()) {
                    if (Objects.equals(entry.getValue().id_V1, neighbor)) {
                        if (entry.getValue().id_V2 == vertexId) {
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1), new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2)));
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2), new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1)));
                            original.edges1.remove(entry.getKey());
                        } else if (neighbors.contains(entry.getValue().id_V2)) {
                            newWeight += entry.getValue().weight;
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1), new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2)));
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2), new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1)));
                            original.edges1.remove(entry.getKey());

                        } else {
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1), new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2)));
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2), new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1)));

                            original.edges1.remove(entry.getKey());
                            if (original.edges1.containsKey(new EdgeKey(entry.getValue().id_V2, vertexId))) {
                                original.edges1.get(new EdgeKey(entry.getValue().id_V2, vertexId)).weight += entry.getValue().weight;
                            } else if (original.edges1.containsKey(new EdgeKey(vertexId, entry.getValue().id_V2))) {
                                original.edges1.get(new EdgeKey(vertexId, entry.getValue().id_V2)).weight += entry.getValue().weight;
                            } else {

                                original.edges1.put(new EdgeKey(vertexId, entry.getValue().id_V2), new Edge(vertexId, entry.getValue().id_V2, entry.getValue().weight));
                                original.edgePairs.add(new EdgePair(new EdgeKey(entry.getValue().id_V2, vertexId), new EdgeKey(vertexId, entry.getValue().id_V2)));
                                original.edgePairs.add(new EdgePair(new EdgeKey(vertexId, entry.getValue().id_V2), new EdgeKey(entry.getValue().id_V2, vertexId)));
                            }
                        }

                    }
                    if (Objects.equals(entry.getValue().id_V2, neighbor)) {
                        if (entry.getValue().id_V1 == vertexId) {
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1), new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2)));
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2), new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1)));
                            original.edges1.remove(entry.getKey());
                        } else if (neighbors.contains(entry.getValue().id_V1)) {
                            newWeight += entry.getValue().weight;
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1), new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2)));
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2), new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1)));
                            original.edges1.remove(entry.getKey());

                        } else {
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1), new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2)));
                            original.edgePairs.remove(new EdgePair(new EdgeKey(entry.getValue().id_V1, entry.getValue().id_V2), new EdgeKey(entry.getValue().id_V2, entry.getValue().id_V1)));

                            original.edges1.remove(entry.getKey());
                            if (original.edges1.containsKey(new EdgeKey(entry.getValue().id_V1, vertexId))) {
                                original.edges1.get(new EdgeKey(entry.getValue().id_V1, vertexId)).weight += entry.getValue().weight;
                            } else if (original.edges1.containsKey(new EdgeKey(vertexId, entry.getValue().id_V1))) {
                                original.edges1.get(new EdgeKey(vertexId, entry.getValue().id_V1)).weight += entry.getValue().weight;
                            } else {
                                original.edges1.put(new EdgeKey(vertexId, entry.getValue().id_V1), new Edge(vertexId, entry.getValue().id_V1, entry.getValue().weight));
                                original.edgePairs.add(new EdgePair(new EdgeKey(entry.getValue().id_V1, vertexId), new EdgeKey(vertexId, entry.getValue().id_V1)));
                                original.edgePairs.add(new EdgePair(new EdgeKey(vertexId, entry.getValue().id_V1), new EdgeKey(entry.getValue().id_V1, vertexId)));
                            }
                        }
                    }

                }
            }

            for (Integer neighbor : neighbors) {
//                Vertex v = original.vertexes.get(neighbor);
//                System.out.println(neighbor);
                original.vertexes.remove(neighbor); //todo: اینجا داری اون راسه رو حذف میکنی.
            }
            original.mergeDuplicateEdges();

            if (original.vertexes.containsKey(vertexId)) {
                original.vertexes.get(vertexId).weight = newWeight;
            } else {
                Vertex newVertex = new Vertex(vertexId, newWeight);
                original.vertexes.put(vertexId, newVertex);
            }
            original.setCost(original.getCost() + newWeight);
            LinkedList<Graph> g = new LinkedList<>();
            g.add(original);

            return original;
        }


        public void mergeDuplicateEdges() {
            Map<EdgeKey, Double> mergedEdges = new HashMap<>();

            for (Map.Entry<EdgeKey, Edge> entry : edges1.entrySet()) {
                EdgeKey key = entry.getKey();
                Edge edge = entry.getValue();

                EdgeKey normalizedKey = new EdgeKey(
                        Math.min(key.id1, key.id2),
                        Math.max(key.id1, key.id2)
                );

                mergedEdges.put(
                        normalizedKey,
                        mergedEdges.getOrDefault(normalizedKey, 0.0) + edge.getWeight()
                );
            }

            edges1.clear();

            for (Map.Entry<EdgeKey, Double> entry : mergedEdges.entrySet()) {
                EdgeKey key = entry.getKey();
                double weight = entry.getValue();
                edges1.put(key, new Edge(key.id1, key.id2, weight));
                edgePairs.add(new EdgePair(key, new EdgeKey(key.id2, key.id1)));
                edgePairs.add(new EdgePair(new EdgeKey(key.id2, key.id1), key));
            }
        }


        public static void addValue(Map<Integer, Set<Integer>> map, int key, int value) {
            map.putIfAbsent(key, new HashSet<>());
            map.get(key).add(value);
        }

        public static void removeValue(Map<Integer, Set<Integer>> map, int key, int value) {
            map.get(key).remove(value);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public Graph deepCopy() {
            Graph copy = new Graph(this.ID);
            copy.cost = getCost();
//            copy.cost = this.cost;

            for (Map.Entry<Integer, Vertex> entry : this.vertexes.entrySet()) {
                Integer vertexId = entry.getKey();
                Vertex originalVertex = entry.getValue();
                Vertex copiedVertex = new Vertex(originalVertex.getId(), originalVertex.getWeight());
                copy.vertexes.put(vertexId, copiedVertex);
            }

            for (Map.Entry<EdgeKey, Edge> entry : this.edges1.entrySet()) {
                EdgeKey originalKey = entry.getKey();
                Edge originalEdge = entry.getValue();
                EdgeKey copiedKey = new EdgeKey(originalKey.getId1(), originalKey.getId2());
                Edge copiedEdge = new Edge(copiedKey.getId1(), copiedKey.getId2(), originalEdge.getWeight());

                copy.edges1.put(copiedKey, copiedEdge);
            }
            for (int i = 0; i < this.edgePairs.size(); i++) {
                copy.edgePairs.add(i, this.edgePairs.get(i));
            }
            return copy;
        }

        public int countConnectedComponents() {
            Set<Integer> visited = new HashSet<>();
            int componentCount = 0;
            for (Integer vertexId : vertexes.keySet()) {
                if (!visited.contains(vertexId)) {
                    dfs(vertexId, visited);
                    componentCount++;
                }
            }
            return componentCount;
        }

        private void dfs(int vertexId, Set<Integer> visited) {
            visited.add(vertexId);
            for (Edge edge : edges1.values()) {
                if (edge.getV1() == vertexId && !visited.contains(edge.getV2())) {
                    dfs(edge.getV2(), visited);
                } else if (edge.getV2() == vertexId && !visited.contains(edge.getV1())) {
                    dfs(edge.getV1(), visited);
                }
            }
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

        public int calculateVertexDegrees(int id) {
//            if(!vertexes.containsKey(id)) return 0;
//            System.out.println("??????????????");
            for (Vertex vertex : vertexes.values()) {
                vertex.deg = 0;
            }
            for (Edge edge : edges1.values()) {
                if (vertexes.containsKey(edge.getV1())) {
                    vertexes.get(edge.getV1()).deg = (vertexes.get(edge.getV1()).deg + 1);
                }
                if (vertexes.containsKey(edge.getV2())) {
                    vertexes.get(edge.getV2()).deg = (vertexes.get(edge.getV2()).deg + 1);
                }
            }
            return vertexes.get(id).deg;
        }

        public Graph contractEdge3(int u, int v) {
            Graph original = this.deepCopy();
            if (!original.vertexes.containsKey(u)) {
                return null;
            }
            if (!original.vertexes.containsKey(v)) {
                return null;
            }
            //todo: اولش
            double weightContractingEdge = 0.0;
            double weightOfV1 = 0.0, weightOfV2 = 0.0;
            int newId = Math.min(u, v);
            int oldId = Math.max(u, v);
            for (Map.Entry<EdgeKey, Edge> entry : original.edges1.entrySet()) {
                if (entry.getKey().id1 == u && entry.getKey().id2 == v || entry.getKey().id1 == v && entry.getKey().id2 == u) {
                    weightContractingEdge = entry.getValue().weight;
                    weightOfV1 = original.vertexes.get(u).weight;
                    weightOfV2 = original.vertexes.get(v).weight;

                }
            }
            original.vertexes.remove(oldId);
            original.vertexes.remove(newId);
//            System.out.println("original vertex size: " + original.vertexes.size());
            original.vertexes.put(newId, new Vertex(newId, weightContractingEdge + weightOfV1 + weightOfV2));
            LinkedList<Edge> edgesCopy = new LinkedList<>(original.edges1.values());

            LinkedList<Edge> edges2 = new LinkedList<>(original.edges1.values());

            for (Edge edge : edges2) {
                if (edge.getV1() == oldId) {
                    EdgeKey key = new EdgeKey(newId, edge.getV2());
                    Edge value = original.edges1.get(new EdgeKey(oldId, edge.getV2()));
                    if (original.edges1.containsKey(new EdgeKey(newId, edge.getV2()))) {
                        original.edges1.get(new EdgeKey(newId, edge.getV2())).weight += value.weight;
                    } else if (original.edges1.containsKey(new EdgeKey(edge.getV2(), newId))) {
                        original.edges1.get(new EdgeKey(edge.getV2(), newId)).weight += value.weight;
                    } else {
                        original.edges1.put(key, new Edge(newId, value.id_V2, value.weight));
                        original.edgePairs.add(new EdgePair(new EdgeKey(newId, edge.getV2()), new EdgeKey(edge.getV2(), newId)));
                        original.edgePairs.add(new EdgePair(new EdgeKey(edge.getV2(), newId), new EdgeKey(newId, edge.getV2())));
                    }


                    original.edges1.remove(new EdgeKey(oldId, edge.getV2()));
                    original.edgePairs.remove(new EdgePair(new EdgeKey(oldId, edge.getV2()), new EdgeKey(edge.getV2(), oldId)));
                    original.edgePairs.remove(new EdgePair(new EdgeKey(edge.getV2(), oldId), new EdgeKey(oldId, edge.getV2())));


                } else if (edge.getV2() == oldId) {
                    EdgeKey key = new EdgeKey(edge.getV1(), newId);
                    Edge value = original.edges1.get(new EdgeKey(edge.getV1(), oldId));
                    if (original.edges1.containsKey(new EdgeKey(newId, edge.getV1()))) {
                        original.edges1.get(new EdgeKey(newId, edge.getV1())).weight += value.weight;
                    } else if (original.edges1.containsKey(new EdgeKey(edge.getV1(), newId))) {
                        original.edges1.get(new EdgeKey(edge.getV1(), newId)).weight += value.weight;
                    } else {
                        original.edges1.put(key, new Edge(value.id_V1, newId, value.weight));
                        original.edgePairs.add(new EdgePair(new EdgeKey(newId, edge.getV1()), new EdgeKey(edge.getV1(), newId)));
                        original.edgePairs.add(new EdgePair(new EdgeKey(edge.getV1(), newId), new EdgeKey(newId, edge.getV1())));
                    }
                    original.edges1.remove(new EdgeKey(edge.getV1(), oldId));
                    original.edgePairs.remove(new EdgePair(new EdgeKey(oldId, edge.getV1()), new EdgeKey(edge.getV1(), oldId)));
                    original.edgePairs.remove(new EdgePair(new EdgeKey(edge.getV1(), oldId), new EdgeKey(oldId, edge.getV1())));
                }
            }
            LinkedList<Edge> edges3 = new LinkedList<>(original.edges1.values());
            for (Edge edge : edges3) {
//                if (edge.getV1()==newId && edge.getV2()==oldId)
                if (Objects.equals(edge.id_V1, edge.id_V2)) {
//                    original.vertexes.get(edge.id_V1).weight += edge.weight; //todo: این احتمالا زیادیه
//                    marginalCost += edge.weight;
//                    original.vertexes.get(edge.id_V1).weight += edge.weight; //todo: :)))
                    original.edges1.remove(new EdgeKey(edge.id_V1, edge.id_V2));
                    original.edgePairs.remove(new EdgePair(new EdgeKey(edge.id_V1, edge.id_V2), new EdgeKey(edge.id_V2, edge.id_V1)));
                    original.edgePairs.remove(new EdgePair(new EdgeKey(edge.id_V2, edge.id_V1), new EdgeKey(edge.id_V1, edge.id_V2)));
                }

            }

            original.setCost(original.getCost() + original.vertexes.get(newId).weight);
            LinkedList<Graph> g = new LinkedList<>();
            g.add(original);
//            System.out.println("g.get(0).getCost() = " + g.get(0).getCost());
//            show_graph2(g);
            return original;
        }


        private void check_id(int id1, int id2, double weight) {
            if (id_edges3.containsKey(id1))
                if (!(id_edges3.get(id1).contains(id2))) {
                    id_edges.put(id1, id2);
                    idChecker2(id1, id2, weight);
                } else Properties.showInvalid();
            else {
                idChecker2(id1, id2, weight);
            }
            Vertex v1 = vertexes.get(id1);
            Vertex v2 = vertexes.get(id2);
            if (v1 != null && v2 != null) {
                v1.addNeighbor(v2);
                v2.addNeighbor(v1);
            }
        }

        private void idChecker2(int id1, int id2, double weight) {
            addValue(id_edges3, id1, id2);
            EdgeKey key = new EdgeKey(id1, id2);
            edges1.put(new EdgeKey(id1, id2), new Edge(id1, id2, weight));
//            vertexes.get(id1).deg++;
//            vertexes.get(id2).deg++;
            edgePairs.add(new EdgePair(new EdgeKey(id1, id2), new EdgeKey(id2, id1)));
            adjacencyMap.computeIfAbsent(id1, k -> new TreeSet<>()).add(key);
            adjacencyMap.computeIfAbsent(id2, k -> new TreeSet<>()).add(key);
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
            return edges1.size();
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
//                    vertexes.get(startVertexId).deg--;
//                    vertexes.get(endVertexId).deg--;
                    removeValue(id_edges3, startVertexId, endVertexId);
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
        private Integer id;
        private final List<Vertex> neighbors;
        public TreeMap<Integer, Double> edges;
        private Double weight;
        private Integer deg = 0;

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

        public void setWeight(Double weight) {
            this.weight = weight;
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

        public Integer getV1() {
            return id_V1;
        }


        public Integer getV2() {
            return id_V2;
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

        public Double getWeight() {
            return weight;
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
        private int id1;
        private int id2;

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
                return Integer.compare(this.id1, other.id1);
            }
            return Integer.compare(this.id2, other.id2);
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
        public final Graph first;
        public final Graph second;

        public Pair(Graph first, Graph second) {
            this.first = first;
            this.second = second;
        }


        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    public static class EdgePair {
        public final EdgeKey first;
        public final EdgeKey second;

        public EdgePair(EdgeKey first, EdgeKey second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    public static class NumberPair {
        public final Integer first;
        public final Integer second;

        public NumberPair(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    public static class GraphCostPair {
        public final Graph graph;
        public final Double cost;

        public GraphCostPair(Graph first, Double second) {
            this.graph = first;
            this.cost = second;
        }

        @Override
        public String toString() {
            return "(" + graph.toString() + ", " + cost.toString() + ")";
        }
    }

    public static class Isomorphism {
        public static boolean areIsomorphic(Graph g1, Graph g2) {
            if (g1.vertexes.size() != g2.vertexes.size() || g1.edges1.size() != g2.edges1.size()) {
                return false;
            }
            Map<Integer, Integer> mapping = new HashMap<>();
            Set<Integer> usedVerticesG1 = new HashSet<>();
            Set<Integer> usedVerticesG2 = new HashSet<>();
            return true;
            //return isIsomorphicHelper(g1, g2, mapping, usedVerticesG1, usedVerticesG2);
        }

        private static boolean isIsomorphicHelper(
                Graph g1, Graph g2,
                Map<Integer, Integer> mapping,
                Set<Integer> usedVerticesG1,
                Set<Integer> usedVerticesG2) {
            if (mapping.size() == g1.vertexes.size()) {
                return true;
            }
            for (Integer v1 : g1.vertexes.keySet()) {
                if (usedVerticesG1.contains(v1)) continue;
                for (Integer v2 : g2.vertexes.keySet()) {
                    if (usedVerticesG2.contains(v2)) continue;
                    mapping.put(v1, v2);
                    usedVerticesG1.add(v1);
                    usedVerticesG2.add(v2);
                    if (areEdgesCompatible(g1, g2, mapping)) {
                        if (isIsomorphicHelper(g1, g2, mapping, usedVerticesG1, usedVerticesG2)) {
                            return true;
                        }
                    }
                    mapping.remove(v1);
                    usedVerticesG1.remove(v1);
                    usedVerticesG2.remove(v2);
                }
            }
            return false;
        }

        static LinkedList<Boolean> flag = new LinkedList<>();
        static LinkedList<Integer> U2 = new LinkedList<>();
        static LinkedList<LinkedList<Integer>> perms = new LinkedList<>();
        static LinkedList<Integer> S = new LinkedList<>();

        static void f() {
            if(S.size() == U2.size()) {
                LinkedList<Integer> S1 = new LinkedList<>(S);
                perms.add(S1);
                return;
            }
            for(int i = 0; i < U2.size(); i ++) {
                if(flag.get(i)) {
                    S.add(U2.get(i));
                    flag.set(i, false);
                    f();
                    S.removeLast();
                    flag.set(i, true);
                }
            }
        }



        public static String distance(boolean boo, Graph g1, Graph g2, boolean print) {
            flag.clear();
            U2.clear();
            LinkedList<Integer> U1 = new LinkedList<>();
            for (Map.Entry<Integer,Vertex> vertex: g2.vertexes.entrySet()){
                U2.add(vertex.getKey());
            }
            for (Map.Entry<Integer,Vertex> vertex: g1.vertexes.entrySet()){
                U1.add(vertex.getKey());
            }
            for(int i = 0; i < U2.size(); i ++) {
                flag.add(true);
            }
            perms.clear();
            f();

            HashMap<Integer, Integer> H = new HashMap<>();

            double minDis = Double.MAX_VALUE;

            for( LinkedList<Integer> perm : perms) {
                H.clear();
//                System.out.println(U1);
//                System.out.println(U1.size());
//                System.out.println("perm: " +perm);
                for(int i = 0; i < U1.size(); i ++) {
//                    System.out.println(i);
                    if(U1.size() != perm.size()) {
//                        System.out.println("ASDfsdfsadfds");
                    }
                    H.put(U1.get(i), perm.get(i));
                }

                double dis = 0;

                for(int i = 0; i < U1.size(); i ++) {
                    int v = U1.get(i);
                    int u = perm.get(i);
//                    System.out.println("u: "+u);
//                    System.out.println("g1.vertexes.get(v).weight: "+g1.vertexes.get(v).weight);
//                    System.out.println("g2.vertexes.get(v).weight: "+g2.vertexes.get(u).weight);
                    dis += Math.abs(g1.vertexes.get(v).weight - g2.vertexes.get(u).weight);
                }

                boolean ok = true;

                for(Map.Entry<EdgeKey,Edge> entry: g1.edges1.entrySet()){
                    Integer v1 = entry.getValue().id_V1;
                    Integer v2 = entry.getValue().id_V2;
                    int u1 = H.get(v1);
                    int u2 =  H.get(v2);
                    double w1 = entry.getValue().weight, w2;
                    if(g2.edges1.containsKey(new EdgeKey(u1,u2))){
                        w2 = g2.edges1.get(new EdgeKey(u1,u2)).weight;
                    }
                    else if(g2.edges1.containsKey(new EdgeKey(u2,u1))){
                        w2 = g2.edges1.get(new EdgeKey(u2,u1)).weight;
                    }
                    else {
                        ok = false;
                        break;
                    }

                    dis += Math.abs(w1 - w2);

                }

                if(ok) {
                    minDis = Math.min(dis, minDis);
                }

            }

            return String.format("%.6f", minDis);

//            double sumWeightVertexesG1 = 0.0, sumWeightVertexesG2 = 0.0, sumWeightEdgesG1 = 0.0, sumWeightEdgesG2 = 0.0, res = 0.0;
//            if (boo) {
//                for (Vertex v1 : g1.vertexes.values()) {
//                    sumWeightVertexesG1 += v1.weight;
//                }
//                for (Vertex v2 : g2.vertexes.values()) {
//                    sumWeightVertexesG2 += v2.weight;
//                }
//                for (Edge e1 : g1.edges1.values()) {
//                    sumWeightEdgesG1 += e1.weight;
//                }
//                for (Edge e2 : g2.edges1.values()) {
//                    sumWeightEdgesG2 += e2.weight;
//                }
//            }
//            res = Math.abs(sumWeightVertexesG1 - sumWeightVertexesG2) + Math.abs(sumWeightEdgesG1 - sumWeightEdgesG2);
//            System.out.println("distance: " + res);
        }

        private static boolean areEdgesCompatible(Graph g1, Graph g2, Map<Integer, Integer> mapping) {
            for (Map.Entry<EdgeKey, Edge> entry : g1.edges1.entrySet()) {
                Edge edge1 = entry.getValue();
                Integer mappedV1 = mapping.get(edge1.getV1());
                Integer mappedV2 = mapping.get(edge1.getV2());
                if (mappedV1 == null || mappedV2 == null) continue;
                EdgeKey mappedKey = new EdgeKey(mappedV1, mappedV2);
                if (!g2.edges1.containsKey(mappedKey)) {
                    return false;
                }
            }
            return true;
        }
    }
}
