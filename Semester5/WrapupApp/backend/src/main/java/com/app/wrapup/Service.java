package com.app.wrapup;

import java.util.ArrayList;
import java.util.List;

public class Service {
    public static List<Node> giftWrapping(Node[] nodes) {
        List<Node> convexHull = new ArrayList<>();

        Node startNode = nodes[0];

        for (int i = 1; i < nodes.length; i++) {
            if (
                nodes[i].getTop() < startNode.getTop()
                    || (nodes[i].getTop() == startNode.getTop()
                            && nodes[i].getLeft() < startNode.getLeft())) {
                startNode = nodes[i];
            }
        }

        Node current = startNode;
        do {
            convexHull.add(current);
            Node next = nodes[0];

            for (int i = 1; i < nodes.length; i++) {
                if (next == current || orientation(current, next, nodes[i]) == 2) {
                    next = nodes[i];
                }
            }

            current = next;

        } while (current != startNode);

        return convexHull;
    }

    // Helper function to determine the orientation of three points
    private static int orientation(Node p, Node q, Node r) {
        double val = (q.getTop() - p.getTop())
                * (r.getLeft() - q.getLeft())
                - (q.getLeft() - p.getLeft())
                        * (r.getTop() - q.getTop());

        if (val == 0)
            return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or Counterclockwise
    }
}