import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * An implementation of the Shallows problem from the 2022 CITS2200 Project
 */
public class ShallowsImpl implements Shallows {
  /**
   * {@inheritdoc}
   */
  public int[] maximumDraughts(int ports, Lane[] lanes, int origin) {
    // TODO: Implement your solution


    int[] colour = new int[ports];

    int[] portDraughts = new int[ports];

    int[] seenPorts = new int[ports];//-1 is untouch vertex
    Arrays.fill(seenPorts,-1);
    Arrays.fill(colour,0);

    int idx = 0;

    for(int i=0; i<ports; i++) {
      portDraughts[i] = 0;
      seenPorts[i] = -1;
    }

    ArrayList<Edge> queue = new ArrayList<>(ports);

    portDraughts[origin] = Integer.MAX_VALUE;

    queue.add(new Edge(origin, portDraughts[origin]));
    Collections.sort(queue);

    while (!queue.isEmpty()) {

      Edge currentRoute = queue.remove(0);
      Collections.sort(queue);
      int arriveVertex = currentRoute.arrive;
      // check if already processed , if processed skip iteration
      if(colour[arriveVertex] == 1) continue;

      portDraughts[arriveVertex] = currentRoute.depth;
      // now has been seen
      colour[arriveVertex] = 1;

      seenPorts[idx] = arriveVertex;
      idx++;

      for (int i = 0; i < lanes.length ; i++) {
        if(lanes[i].depart == arriveVertex && lanes[i].arrive != origin && colour[lanes[i].arrive] != 1) {
          Collections.sort(queue);
          Edge a = new Edge(lanes[i].arrive, lanes[i].depth);
          portDraughts[lanes[i].arrive] = lanes[i].depth;
            queue.add(a);
        }
      }
    }

    int min = Integer.MAX_VALUE;
    for(int i=0; i<portDraughts.length;i++) {
      if(seenPorts[i] == -1 || seenPorts[i] == origin) continue;
      if(portDraughts[seenPorts[i]]<=min) {
        min = portDraughts[seenPorts[i]];
      }
      else {
        portDraughts[seenPorts[i]] = min;
      }
    }
    return portDraughts;
  }
}


class Edge implements Comparable<Edge> {
  int arrive; int depth;

  public Edge(int arrive, int depth) {

    this.arrive = arrive;
    this.depth = depth;
  }

  public int compareTo(Edge e) {

    if(this.depth < e.depth) return 1;
    if(this.depth > e.depth) return -1;
    return 0;
  }
}
