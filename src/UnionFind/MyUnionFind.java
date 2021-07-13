package UnionFind;

//Code does not work properly :(
//Good explanation --> https://www.youtube.com/watch?v=ayW5B2W9hfo
public class MyUnionFind {


    public static void main(String[] args) {
        /* Let us create the following graph
        0
        | \
        |  \
        1---2 */
        int V = 3, E = 3;
        Graph graph = new Graph(V, E);

        // add edge 0-1
        graph.edges[0].source = 0;
        graph.edges[0].destination = 1;

        // add edge 1-2
        graph.edges[1].source = 1;
        graph.edges[1].destination = 2;

////        // add edge 0-2
//        graph.edges[2].source = 0;
//        graph.edges[2].destination = 2;


        System.out.println(graph.isThereACycle());

    }

    static class Graph {
        int verticesCount;
        int edgesCount;
        int[] parent;
        Edge[] edges;

        public Graph(int verticesCount, int edgesCount) {
            this.verticesCount = verticesCount;
            this.edgesCount = edgesCount;

            edges = new Edge[edgesCount];
            for (int i = 0; i < edgesCount; ++i)
                edges[i] = new Edge();

            parent = new int[verticesCount];

        }


        private class Edge {
            int source;
            int destination;
        }


        private int find(int element) {
            for (int i : parent) parent[i] = i;

            if (element != parent[element])
                return find(parent[element]);
            return element;
        }

        private void union(int elementOfFirstSet, int elementOfSecondSet) {
            parent[find(elementOfSecondSet)] = find(elementOfFirstSet);
        }


        public boolean isThereACycle() {
            for (Edge edge : this.edges) {
                int setOfTheSource = this.find(edge.source);
                int setOfTheDestination = this.find(edge.destination);

                if (setOfTheSource == setOfTheDestination)
                    return true;

                union(setOfTheSource, setOfTheDestination);
            }
            return false;
        }

    }
}


