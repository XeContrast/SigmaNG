package info.sigmaclient.sigma.sigma5.utils;

import java.util.ArrayList;
import java.util.List;

public class SomeAnim {


    public static class Point {
        private double x;
        private double y;

        public Point() {
            this.x = 0.0;
            this.y = 0.0;
        }

        public Point(final double x, final double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }
    }

    public static float interpolate(final float n, final double... array) {
        final ArrayList list = new ArrayList();
        list.add(new Point(0.0, 0.0));
        list.add(new Point(array[0], array[1]));
        list.add(new Point(array[2], array[3]));
        list.add(new Point(1.0, 1.0));
        return (float)new BezierCurve(0.0055555556900799274).evaluate(list, n);
    }
    public static class BezierCurve {
        private double step;
        public static final double 褕郝贞曞ꁈ = 0.10000000149011612;

        public BezierCurve(final double step) {
            if (step > 0.0 && step < 1.0) {
                this.step = step;
                return;
            }
        }

        public BezierCurve() {
            this(0.10000000149011612);
        }

        public Point quadraticBezier(final Point Point, final Point Point2, final Point Point3, final double n) {
            return new Point((1.0 - n) * (1.0 - n) * Point.getX() + 2.0 * n * (1.0 - n) * Point2.getX() + n * n * Point3.getX(), (1.0 - n) * (1.0 - n) * Point.getY() + 2.0 * n * (1.0 - n) * Point2.getY() + n * n * Point3.getY());
        }

        public Point cubicBezier(final Point Point, final Point Point2, final Point Point3, final Point Point4, final double n) {
            final double n2 = 1.0 - n;
            final double n3 = n2 * n2;
            final double n4 = n3 * n2;
            return new Point(Point.getX() * n4 + Point2.getX() * 3.0 * n * n3 + Point3.getX() * 3.0 * n * n * n2 + Point4.getX() * n * n * n, Point.getY() * n4 + Point2.getY() * 3.0 * n * n3 + Point3.getY() * 3.0 * n * n * n2 + Point4.getY() * n * n * n);
        }

        public double evaluate(final List<Point> list, final float n) {
            if (n != 0.0f) {
                final List<Point> points = this.generatePoints(list);
                double n2 = 1.0;
                for (int i = 0; i < points.size(); ++i) {
                    final Point Point = points.get(i);
                    if (Point.getX() > n) {
                        break;
                    }
                    final double y = Point.getY();
                    Point Point2 = new Point(1.0, 1.0);
                    if (i + 1 < points.size()) {
                        Point2 = points.get(i + 1);
                    }
                    n2 = y + (Point2.getY() - Point.getY()) * ((n - Point.getX()) / (Point2.getX() - Point.getX()));
                }
                return n2;
            }
            return 0.0;
        }

        public List<Point> generatePoints(final List<Point> list) {
            if (list.size() >= 3) {
                final Point Point = list.get(0);
                final Point Point2 = list.get(1);
                final Point Point3 = list.get(2);
                final Point Point4 = (list.size() != 4) ? null : list.get(3);
                final ArrayList list2 = new ArrayList();
                Point Point5 = Point;
                for (double n = 0.0; n < 1.0; n += this.step) {
                    list2.add(Point5);
                    Point5 = ((Point4 != null) ? this.cubicBezier(Point, Point2, Point3, Point4, n) : this.quadraticBezier(Point, Point2, Point3, n));
                }
                return list2;
            }
            return null;
        }
    }
}
