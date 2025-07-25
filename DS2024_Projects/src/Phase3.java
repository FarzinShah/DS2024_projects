import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Phase3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Graph> alphaGraphs = new ArrayList<>();
        Graph currentGraph = null;
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.equalsIgnoreCase("READ_TEXT")) break;
            if (line.isEmpty()) continue;

            StringTokenizer tokenizer = new StringTokenizer(line);
            String command = tokenizer.nextToken().toUpperCase();

            switch (command) {
                case "NEW_GRAPH":
                    if (!tokenizer.hasMoreTokens()) {
                        System.out.println("Error: NEW_GRAPH requires a name.");
                        continue;
                    }
                    currentGraph = new Graph(tokenizer.nextToken());
                    alphaGraphs.add(currentGraph);
                    break;

                case "ADD_VERTEX":
                    if (currentGraph == null) {
                        System.out.println("Error: No active graph. Use NEW_GRAPH first.");
                        continue;
                    }
                    if (tokenizer.countTokens() < 3) {
                        System.out.println("Error: ADD_VERTEX requires 3 arguments (id x y).");
                        continue;
                    }
                    try {
                        int id = Integer.parseInt(tokenizer.nextToken());
                        int x = Integer.parseInt(tokenizer.nextToken());
                        int y = Integer.parseInt(tokenizer.nextToken());
                        currentGraph.vertexes.put(id, new Vertex(id, x, y));
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid numbers in ADD_VERTEX.");
                    }
                    break;

                case "ADD_EDGE":
                    if (currentGraph == null) {
                        System.out.println("Error: No active graph. Use NEW_GRAPH first.");
                        continue;
                    }
                    if (tokenizer.countTokens() < 2) {
                        System.out.println("Error: ADD_EDGE requires 2 arguments (from to).");
                        continue;
                    }
                    try {
                        int from = Integer.parseInt(tokenizer.nextToken());
                        int to = Integer.parseInt(tokenizer.nextToken());
                        currentGraph.addEdge2(from, to);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid numbers in ADD_EDGE.");
                    }
                    break;

                default:
                    System.out.println("Error: Unknown command '" + command + "'.");
            }
        }

        if(alphaGraphs.size() == 1){
            if (alphaGraphs.get(0).vertexes.isEmpty()){
                System.out.println(alphaGraphs.get(0).ID);
                System.exit(0);
            }
        }
        int n = 0;
        String n1 = reader.readLine().trim();
        if (Properties.isNumeric(n1)) {
            n = Integer.parseInt(n1);
        }



        ArrayList<Graph> graphs = new ArrayList<>();
        Graph mainGraph = new Graph("MAIN");

        for (int k = 0; k < n; k++) {
            String input = reader.readLine().trim();
            StringTokenizer tokenizer = new StringTokenizer(input);
            String command = tokenizer.nextToken();

            switch (command) {
                case "ADD_VERTEX":
                    try {
                        int id = Integer.parseInt(tokenizer.nextToken());
                        int x = Integer.parseInt(tokenizer.nextToken());
                        int y = Integer.parseInt(tokenizer.nextToken());
                        mainGraph.vertexes.putIfAbsent(id, new Vertex(id, x, y));
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid input for ADD_VERTEX.");
                    }
                    break;

                case "ADD_EDGE":
                    try {
                        int from = Integer.parseInt(tokenizer.nextToken());
                        int to = Integer.parseInt(tokenizer.nextToken());
                        mainGraph.addEdge2(from, to);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid input for ADD_EDGE.");
                    }
                    break;

                case "DEL_VERTEX":
                    mainGraph.deleteVertex(Integer.parseInt(tokenizer.nextToken()));
                    break;

                case "DEL_EDGE":
                    mainGraph.deleteEdge(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()));
                    break;

                case "CCC":
                    System.out.println(mainGraph.countConnectedComponents());
                    break;

                case "MERGE_VERTEX":
                    show_graph(tokenizer.nextToken(), graphs);
                    break;

                case "GET_DEGREE":
                    System.out.println(mainGraph.calculateVertexDegrees(Integer.parseInt(tokenizer.nextToken())));
                    break;


            }
        }

        if(alphaGraphs.isEmpty()){
            System.exit(0);
        }


        graphs = mainGraph.getConnectedComponents();
        ArrayList<Tuple> finalRes = finalResult(alphaGraphs, graphs);

        NavigableMap<Integer, List<Tuple>> groupedByY = new TreeMap<>();
        for (Tuple t : finalRes) {
            groupedByY.computeIfAbsent(t.getY(), k -> new ArrayList<>()).add(t);
        }

        for (var entry : groupedByY.descendingMap().entrySet()) {
            entry.getValue().sort(Comparator.comparing(Tuple::getX).thenComparing(Tuple::getS));
            for (Tuple t : entry.getValue()) System.out.print(t.getS());
            System.out.println();
        }
    }



    public static ArrayList<Tuple> finalResult(ArrayList<Graph> alpha, ArrayList<Graph> subDisConnectedGraphs) {
        ArrayList<Tuple> res = new ArrayList<>();
        HashMap<Double, Tuple> tempTempo = new HashMap<>();
        double resBFS;
        double q = Double.MAX_VALUE;
        int tempX = 999999999,tempY=999999999;
        int j = 0;
        String charAppending = "";
        for (Graph subDisConnectedGraph : subDisConnectedGraphs) {
            Graph g2CC = subDisConnectedGraph.deepCopy();
            for (Graph graph : alpha) {
                Graph g1CC = graph.deepCopy();

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
                    l1.add(z);
                    q1.addAll(result3(z));
                }
                while (!q2.isEmpty()) {
                    Graph z2 = q2.remove();
                    l2.add(z2);
                    q2.addAll(result3(z2));
                }
                resBFS = getMinCost(l1, l2);
                charAppending = g1CC.getID();

                for (Vertex entry: subDisConnectedGraph.vertexes.values()) {
                    if(entry.y < tempY){
                        tempY = entry.y;
                    }
                    if (entry.x < tempX){
                        tempX = entry.x;
                    }
                }
                if(tempTempo.containsKey(resBFS)){
                    if(tempTempo.get(resBFS).s.compareTo(charAppending)>0){
                        tempTempo.remove(resBFS);
                        tempTempo.put(resBFS, new Tuple(charAppending,tempX,tempY));

                    }
                }else
                    tempTempo.put(resBFS, new Tuple(charAppending,tempX,tempY));
                tempX = 999999999; tempY = 999999999;


            }
            for (Double t : tempTempo.keySet()) {
                if (t < q) {
                    q = t;
                }
            }
            res.add(tempTempo.get(q));
            q = Double.MAX_VALUE;
            j++;
            tempTempo.clear();
        }
        //todo: خب الان چطوری اختلاف y هاشون رو تشخیص بدم؟
        return res;
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
            if (!Objects.equals(entry.getValue().id_V1, entry.getValue().id_V2)) {
                Graph contracted = g2.contractEdge3(entry.getValue().id_V1, entry.getValue().id_V2);
                if (contracted != null)
                    res.add(contracted);
            }

        }

        return res;
    }

    public static int getVertexDegree(Graph graph, int vertexId) {
        if (!graph.vertexes.containsKey(vertexId)) {
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
                    double currentCost = Double.parseDouble(Isomorphism.distance(true, graph, value, false));
                    if (result > currentCost + graph.getCost() + value.getCost()) {
                        result = currentCost + graph.getCost() + value.getCost();
                    }
                }
            }
        }
        return result == Double.MAX_VALUE ? Double.POSITIVE_INFINITY : result;
    }


    public static void show_graph(String id, ArrayList<Graph> graphs) {
        StringBuilder ans = new StringBuilder();
        Graph g = null;
        for (Graph graph : graphs) {
            if (graph.ID.equals(id)) {
                g = graph;
                break;
            }
        }
        if ((g != null))
            if (Objects.equals(g.getID(), id)) {
                ans.append(g.getID()).append(" ").append(g.getVertexes().size()).append(" ").append(g.edges1.size()).append("\n");
                for (Map.Entry<Integer, Vertex> entry : g.vertexes.entrySet()) {
                    ans.append(id).append(" ").append(entry.getKey()).append(" ").append(String.format("%.6f", entry.getValue().getWeight())).append("\n");
                }
                for (Map.Entry<EdgeKey, Edge> entry : g.edges1.entrySet()) {
                    ans.append(id).append(" ").append(entry.getValue().id_V1).append(" ").append(entry.getValue().id_V2).append(" ").append(String.format("%.6f", entry.getValue().weight)).append("\n");
                }
                System.out.print(ans);
            } else Properties.showInvalid();
    }


    public static class Graph {
        public TreeMap<Integer, Vertex> vertexes;
        public TreeMap<EdgeKey, Edge> edges1;
        public Set<PairE> edgePairs2;

        Map<Integer, TreeSet<EdgeKey>> adjacencyMap = new HashMap<>();

        private final TreeMap<Integer, Integer> id_edges;
        private TreeMap<Integer, Set<Integer>> id_edges3;

        private final LinkedList<Pair> id_edges2;

        private double cost;
        private String ID;

        public Graph(String ID) {
            vertexes = new TreeMap<>();
            edges1 = new TreeMap<>();
            id_edges = new TreeMap<>();
            id_edges2 = new LinkedList<>();
            id_edges3 = new TreeMap<>();
            edgePairs2 = new HashSet<>();
            this.ID = ID;
            this.cost = 0.0;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }


        public ArrayList<Graph> getConnectedComponents() {
            Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
            for (int v : vertexes.keySet()) {
                adjacencyList.put(v, new ArrayList<>());
            }
            for (Edge e : edges1.values()) {
                adjacencyList.get(e.id_V1).add(e.id_V2);
                adjacencyList.get(e.id_V2).add(e.id_V1);
            }

            ArrayList<Graph> components = new ArrayList<>();
            Set<Integer> visited = new HashSet<>();

            int i = 1;
            for (int vertexId : vertexes.keySet()) {
                if (!visited.contains(vertexId)) {
                    Graph component = new Graph(ID+"c"+i);
                    dfs(vertexId, visited, component, adjacencyList);
                    components.add(component);
                    i++;
                }
            }

            return components;
        }

        private void dfs(int v, Set<Integer> visited, Graph component, Map<Integer, List<Integer>> adjacencyList) {
            Stack<Integer> stack = new Stack<>();
            stack.push(v);
            visited.add(v);

            while (!stack.isEmpty()) {
                int current = stack.pop();
                Vertex vertex = vertexes.get(current);
                component.vertexes.put(vertex.id,new Vertex(vertex.id, vertex.x, vertex.y));

                for (int neighbor : adjacencyList.get(current)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        stack.push(neighbor);
                    }
                    if (component.vertexes.containsKey(neighbor)) {
                        component.addEdge2(current, neighbor);
                    }
                }
            }
        }


        public Graph contractVertex3(int vertexId) {
            Graph original = this.deepCopy();
            Vertex vertexToContract = original.vertexes.get(vertexId);
            if (vertexToContract == null) {
                return original;
            }
            int VTC_X = original.vertexes.get(vertexId).x;
            int VTC_Y = original.vertexes.get(vertexId).y;

            double totalWeight = vertexToContract.getWeight();
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
                            original.edgePairs2.remove(new PairE(entry.getValue().id_V2, entry.getValue().id_V1));
                            original.edges1.remove(entry.getKey());
                        } else if (neighbors.contains(entry.getValue().id_V2)) {
                            newWeight += entry.getValue().weight;
                            original.edgePairs2.remove(new PairE(entry.getValue().id_V2, entry.getValue().id_V1));
                            original.edges1.remove(entry.getKey());

                        } else {
                            original.edgePairs2.remove(new PairE(entry.getValue().id_V2, entry.getValue().id_V1));
                            original.edges1.remove(entry.getKey());
                            if (original.edges1.containsKey(new EdgeKey(entry.getValue().id_V2, vertexId))) {
                                original.edges1.get(new EdgeKey(entry.getValue().id_V2, vertexId)).weight += entry.getValue().weight;
                            } else if (original.edges1.containsKey(new EdgeKey(vertexId, entry.getValue().id_V2))) {
                                original.edges1.get(new EdgeKey(vertexId, entry.getValue().id_V2)).weight += entry.getValue().weight;
                            } else {
                                original.edges1.put(new EdgeKey(vertexId, entry.getValue().id_V2), new Edge(vertexId, entry.getValue().id_V2, entry.getValue().weight));
                                original.edgePairs2.add(new PairE(vertexId, entry.getValue().id_V2));
                            }
                        }

                    }
                    if (Objects.equals(entry.getValue().id_V2, neighbor)) {
                        if (entry.getValue().id_V1 == vertexId) {
                            original.edgePairs2.remove(new PairE(entry.getValue().id_V2, entry.getValue().id_V1));
                            original.edges1.remove(entry.getKey());
                        } else if (neighbors.contains(entry.getValue().id_V1)) {
                            newWeight += entry.getValue().weight;
                            original.edgePairs2.remove(new PairE(entry.getValue().id_V2, entry.getValue().id_V1));
                            original.edges1.remove(entry.getKey());

                        } else {
                            original.edgePairs2.remove(new PairE(entry.getValue().id_V2, entry.getValue().id_V1));
                            original.edges1.remove(entry.getKey());
                            if (original.edges1.containsKey(new EdgeKey(entry.getValue().id_V1, vertexId))) {
                                original.edges1.get(new EdgeKey(entry.getValue().id_V1, vertexId)).weight += entry.getValue().weight;
                            } else if (original.edges1.containsKey(new EdgeKey(vertexId, entry.getValue().id_V1))) {
                                original.edges1.get(new EdgeKey(vertexId, entry.getValue().id_V1)).weight += entry.getValue().weight;
                            } else {
                                original.edges1.put(new EdgeKey(vertexId, entry.getValue().id_V1), new Edge(vertexId, entry.getValue().id_V1, entry.getValue().weight));
                                original.edgePairs2.remove(new PairE(entry.getValue().id_V2, entry.getValue().id_V1));
                            }
                        }
                    }

                }
            }

            for (Integer neighbor : neighbors) {
                original.vertexes.remove(neighbor);
            }
            original.mergeDuplicateEdges();

            if (original.vertexes.containsKey(vertexId)) {
                original.vertexes.get(vertexId).weight = newWeight;
            } else {
                Vertex newVertex = new Vertex(vertexId, VTC_X, VTC_Y);
                newVertex.weight = newWeight; //todo: اگه وزن همه راس ها ثابت و برابر 1000 باشه دیگه به این نیاز نیست!!!
                original.vertexes.put(vertexId, newVertex);
            }
            original.setCost(original.getCost() + newWeight);

            return original;
        }


        public void mergeDuplicateEdges() {
            Map<EdgeKey, Double> mergedEdges = new HashMap<>();

            for (Map.Entry<EdgeKey, Edge> entry : edges1.entrySet()) {
                EdgeKey key = entry.getKey();
                Edge edge = entry.getValue();

                EdgeKey normalizedKey = new EdgeKey(Math.min(key.id1, key.id2), Math.max(key.id1, key.id2));

                mergedEdges.put(normalizedKey, mergedEdges.getOrDefault(normalizedKey, edge.getWeight()));
            }

            edges1.clear();
            edgePairs2.clear();

            for (Map.Entry<EdgeKey, Double> entry : mergedEdges.entrySet()) {
                EdgeKey key = entry.getKey();
                double weight = entry.getValue();
                edges1.put(key, new Edge(key.id1, key.id2, weight));
                edgePairs2.add(new PairE(key.id1, key.id2));

            }

        }


        public static void addValue(Map<Integer, Set<Integer>> map, int key, int value) {
            map.putIfAbsent(key, new HashSet<>());
            map.get(key).add(value);
        }

        public static void removeValue(Map<Integer, Set<Integer>> map, int key, int value) {
            map.get(key).remove(value);
        }

        public Graph deepCopy() {
            Graph copy = new Graph(this.ID);
            copy.cost = this.cost;
            for (Map.Entry<Integer, Vertex> entry : this.vertexes.entrySet()) {
                Integer vertexId = entry.getKey();
                Vertex originalVertex = entry.getValue();
                Vertex copiedVertex = new Vertex(originalVertex.getId(), originalVertex.x, originalVertex.y);
                copiedVertex.weight = originalVertex.weight; //todo: اگه وزن یالا ثابت و برابر 1000 بمونه دیگه این اضافیه!
                copy.vertexes.put(vertexId, copiedVertex);
            }

            for (Map.Entry<EdgeKey, Edge> entry : this.edges1.entrySet()) {
                EdgeKey originalKey = entry.getKey();
                Edge originalEdge = entry.getValue();
                EdgeKey copiedKey = new EdgeKey(originalKey.getId1(), originalKey.getId2());
                Edge copiedEdge = new Edge(copiedKey.getId1(), copiedKey.getId2(), originalEdge.getWeight());
                copy.edges1.put(copiedKey, copiedEdge);
            }
            copy.edgePairs2 = new HashSet<>(this.edgePairs2);
            copy.id_edges3 = new TreeMap<>(this.id_edges3);

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

        public Double euclideanDistance(int x1, int y1, int x2, int y2) {
            return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
        }

        public void addEdge2(int id1, int id2) {
            try {
                if (vertexes.containsKey(id1) && vertexes.containsKey(id2)) {
                    if (id_edges3.containsKey(id1)) {
                        if (!(id_edges3.get(id1).contains(id2))) {
                            check_id(id1, id2, euclideanDistance(vertexes.get(id1).x, vertexes.get(id1).y, vertexes.get(id2).x, vertexes.get(id2).y));
                        }
                    } else {
                        check_id(id1, id2, euclideanDistance(vertexes.get(id1).x, vertexes.get(id1).y, vertexes.get(id2).x, vertexes.get(id2).y));
                    }
                }
            } catch (Exception e) {
                Properties.showInvalid();
            }
        }

        public int calculateVertexDegrees(int id) {

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

        public Graph contractEdge3(Integer u, Integer v) {
            Graph original = this.deepCopy();
            if (!original.vertexes.containsKey(u)) {
                return null;
            }
            if (!original.vertexes.containsKey(v)) {
                return null;
            }
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
            int OLD_ID_x = original.vertexes.get(oldId).x;
            int OLD_ID_y = original.vertexes.get(oldId).y;
            original.vertexes.remove(oldId);
            original.vertexes.remove(newId);
            Vertex newVertex = new Vertex(newId, OLD_ID_x, OLD_ID_y);
            newVertex.weight = weightContractingEdge + weightOfV1 + weightOfV2; //todo: اگه وزن رئوس ثابت و برابر 1000 باشه، این اضافیه.
            original.vertexes.put(newId, newVertex);

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
                        original.edgePairs2.add(new PairE(newId, edge.getV2()));
                    }


                    original.edges1.remove(new EdgeKey(oldId, edge.getV2()));
                    original.edgePairs2.remove(new PairE(oldId, edge.getV2()));


                } else if (edge.getV2() == oldId) {
                    EdgeKey key = new EdgeKey(edge.getV1(), newId);
                    Edge value = original.edges1.get(new EdgeKey(edge.getV1(), oldId));
                    if (original.edges1.containsKey(new EdgeKey(newId, edge.getV1()))) {
                        original.edges1.get(new EdgeKey(newId, edge.getV1())).weight += value.weight;
                    } else if (original.edges1.containsKey(new EdgeKey(edge.getV1(), newId))) {
                        original.edges1.get(new EdgeKey(edge.getV1(), newId)).weight += value.weight;
                    } else {
                        original.edges1.put(key, new Edge(value.id_V1, newId, value.weight));
                        original.edgePairs2.add(new PairE(newId, edge.getV1()));
                    }
                    original.edges1.remove(new EdgeKey(edge.getV1(), oldId));
                    original.edgePairs2.remove(new PairE(oldId, edge.getV1()));

                }
            }
            LinkedList<Edge> edges3 = new LinkedList<>(original.edges1.values());
            for (Edge edge : edges3) {
                if (Objects.equals(edge.id_V1, edge.id_V2)) {
                    original.edges1.remove(new EdgeKey(edge.id_V1, edge.id_V2));
                }


            }
            original.setCost(original.getCost() + original.vertexes.get(newId).weight);

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
            edgePairs2.add(new PairE(id1, id2));
            adjacencyMap.computeIfAbsent(id1, k -> new TreeSet<>()).add(key);
            adjacencyMap.computeIfAbsent(id2, k -> new TreeSet<>()).add(key);
        }

        public String getID() {
            return ID;
        }

        public TreeMap<Integer, Vertex> getVertexes() {
            return vertexes;
        }

        public void deleteEdge(int startVertexId, int endVertexId) {
            EdgeKey e = new EdgeKey(startVertexId, endVertexId);

            if (vertexes.containsKey(startVertexId) && vertexes.containsKey(endVertexId)) {
                if (edges1.containsKey(e)) {
                    removeEdge1(edges1, adjacencyMap, new EdgeKey(startVertexId, endVertexId));

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
        private Integer x, y;
        private double weight = 1000.0;
        private final List<Vertex> neighbors;
        private Integer deg = 0;

        public Vertex(Integer id, Integer x, Integer y) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.neighbors = new LinkedList<>();

        }

        public Integer getId() {
            return id;
        }

        public Double getWeight() {
            return weight;
        }

        public void addNeighbor(Vertex neighbor) {
            neighbors.add(neighbor);
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


        static LinkedList<Boolean> flag = new LinkedList<>();
        static LinkedList<Integer> U2 = new LinkedList<>();
        static LinkedList<LinkedList<Integer>> perms = new LinkedList<>();
        static LinkedList<Integer> S = new LinkedList<>();

        static void f() {
            if (S.size() == U2.size()) {
                LinkedList<Integer> S1 = new LinkedList<>(S);
                perms.add(S1);
                return;
            }
            for (int i = 0; i < U2.size(); i++) {
                if (flag.get(i)) {
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
            for (Map.Entry<Integer, Vertex> vertex : g2.vertexes.entrySet()) {
                U2.add(vertex.getKey());
            }
            for (Map.Entry<Integer, Vertex> vertex : g1.vertexes.entrySet()) {
                U1.add(vertex.getKey());
            }
            for (int i = 0; i < U2.size(); i++) {
                flag.add(true);
            }
            perms.clear();
            f();

            HashMap<Integer, Integer> H = new HashMap<>();

            double minDis = Double.MAX_VALUE;

            for (LinkedList<Integer> perm : perms) {
                H.clear();

                for (int i = 0; i < U1.size(); i++) {
                    H.put(U1.get(i), perm.get(i));
                }

                double dis = 0;

                for (int i = 0; i < U1.size(); i++) {
                    int v = U1.get(i);
                    int u = perm.get(i);
                    dis += Math.abs(g1.vertexes.get(v).weight - g2.vertexes.get(u).weight);
                }

                boolean ok = true;

                for (Map.Entry<EdgeKey, Edge> entry : g1.edges1.entrySet()) {
                    Integer v1 = entry.getValue().id_V1;
                    Integer v2 = entry.getValue().id_V2;
                    int u1 = H.get(v1);
                    int u2 = H.get(v2);
                    double w1 = entry.getValue().weight, w2;
                    if (g2.edges1.containsKey(new EdgeKey(u1, u2))) {
                        w2 = g2.edges1.get(new EdgeKey(u1, u2)).weight;
                    } else if (g2.edges1.containsKey(new EdgeKey(u2, u1))) {
                        w2 = g2.edges1.get(new EdgeKey(u2, u1)).weight;
                    } else {
                        ok = false;
                        break;
                    }

                    dis += Math.abs(w1 - w2);

                }

                if (ok) {
                    minDis = Math.min(dis, minDis);
                }

            }

            return String.format("%.6f", minDis);
        }

    }

    public static class PairE {
        private final int first;
        private final int second;

        // Constructor
        public PairE(int a, int b) {
            if (a < b) {
                this.first = a;
                this.second = b;
            } else {
                this.first = b;
                this.second = a;
            }
        }

        // Override equals method
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            PairE pair = (PairE) obj;
            return first == pair.first && second == pair.second;
        }

        // Override hashCode method
        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }
    public static class Tuple{
        private String s;
        private Integer x;
        private Integer y;

        public Tuple(String s, Integer x, Integer y) {
            this.s = s;
            this.x = x;
            this.y = y;
        }

        public String getS() {
            return s;
        }

        public Integer getX() {
            return x;
        }

        public Integer getY() {
            return y;
        }

        @Override
        public String toString() {
            return "(alpha: " + s + ", x: " + x +", y: " + y + ")";
        }
    }
}
