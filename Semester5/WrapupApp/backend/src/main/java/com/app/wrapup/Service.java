package com.app.wrapup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Service {

    private static Node findPivot(List<Node> points) {
        Node pivot = points.get(0);
        for (Node point : points) {
            if (point.getTop() < pivot.getTop()
                    || (point.getTop() == pivot.getTop() && point.getLeft() < pivot.getLeft())) {
                pivot = point;
            }
        }
        return pivot;
    }

    private static void sortByAngle(List<Node> points, Node pivot) {
        points.sort(Comparator.comparingDouble(
                point -> Math.atan2(point.getTop() - pivot.getTop(), point.getLeft() - pivot.getLeft())));
    }

    public static List<Node> giftWrapping(List<Node> points) {
        if (points.size() < 3) {

            return points;
        }

        List<Node> convexHull = new ArrayList<>();

        Node pivot = findPivot(points);

        sortByAngle(points, pivot);

        convexHull.add(points.get(0));
        convexHull.add(points.get(1));
        convexHull.add(points.get(2));

        for (int i = 3; i < points.size(); i++) {

            while (orientation(convexHull.get(convexHull.size() - 2), convexHull.get(convexHull.size() - 1),
                    points.get(i)) != 2) {
                convexHull.remove(convexHull.size() - 1);
            }
            convexHull.add(points.get(i));
        }

        return convexHull;
    }

    private static int orientation(Node p, Node q, Node r) {
        double val = (q.getTop() - p.getTop()) * (r.getLeft() - q.getLeft())
                - (q.getLeft() - p.getLeft()) * (r.getTop() - q.getTop());
        if (val == 0) {
            return 0;
        }
        return (val > 0) ? 1 : 2;
    }

}