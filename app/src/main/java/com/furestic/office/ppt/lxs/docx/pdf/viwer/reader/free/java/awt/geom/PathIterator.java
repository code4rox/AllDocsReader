/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.java.awt.geom;

/**
 * The <code>PathIterator</code> interface provides the mechanism 
 * for objects that implement the {@link com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.usermodel.Shape Shape}
 * interface to return the geometry of their boundary by allowing
 * a caller to retrieve the path of that boundary a segment at a
 * time.  This interface allows these objects to retrieve the path of
 * their boundary a segment at a time by using 1st through 3rd order
 * B&eacute;zier curves, which are lines and quadratic or cubic
 * B&eacute;zier splines.
 * <p>
 * Multiple subpaths can be expressed by using a "MOVETO" segment to
 * create a discontinuity in the geometry to move from the end of
 * one subpath to the beginning of the next.
 * <p>
 * Each subpath can be closed manually by ending the last segment in
 * the subpath on the same coordinate as the beginning "MOVETO" segment
 * for that subpath or by using a "CLOSE" segment to append a line
 * segment from the last point back to the first.
 * Be aware that manually closing an outline as opposed to using a
 * "CLOSE" segment to close the path might result in different line
 * style decorations being used at the end points of the subpath.
 * For example, the {@link com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.achartengine.renderers.BasicStroke BasicStroke} object
 * uses a line "JOIN" decoration to connect the first and last points 
 * if a "CLOSE" segment is encountered, whereas simply ending the path 
 * on the same coordinate as the beginning coordinate results in line
 * "CAP" decorations being used at the ends.
 *
 * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.fc.hwpf.usermodel.Shape
 * @see com.furestic.office.ppt.lxs.docx.pdf.viwer.reader.free.thirdpart.achartengine.renderers.BasicStroke
 *
 * @version %I%, %G%
 * @author Jim Graham
 */
public interface PathIterator {
    /**
     * The winding rule constant for specifying an even-odd rule
     * for determining the interior of a path.
     * The even-odd rule specifies that a point lies inside the
     * path if a ray drawn in any direction from that point to
     * infinity is crossed by path segments an odd number of times.
     */
    public static final int WIND_EVEN_ODD	= 0;

    /**
     * The winding rule constant for specifying a non-zero rule
     * for determining the interior of a path.
     * The non-zero rule specifies that a point lies inside the
     * path if a ray drawn in any direction from that point to
     * infinity is crossed by path segments a different number
     * of times in the counter-clockwise direction than the
     * clockwise direction.
     */
    public static final int WIND_NON_ZERO	= 1;

    /**
     * The segment type constant for a point that specifies the
     * starting location for a new subpath.
     */
    public static final int SEG_MOVETO		= 0;

    /**
     * The segment type constant for a point that specifies the
     * end point of a line to be drawn from the most recently
     * specified point.
     */
    public static final int SEG_LINETO		= 1;

    /**
     * The segment type constant for the pair of points that specify
     * a quadratic parametric curve to be drawn from the most recently
     * specified point.
     * The curve is interpolated by solving the parametric control
     * equation in the range <code>(t=[0..1])</code> using
     * the most recently specified (current) point (CP),
     * the first control point (P1),
     * and the final interpolated control point (P2).
     * The parametric control equation for this curve is:
     * <pre>
     *          P(t) = B(2,0)*CP + B(2,1)*P1 + B(2,2)*P2
     *          0 &lt;= t &lt;= 1
     *
     *        B(n,m) = mth coefficient of nth degree Bernstein polynomial
     *               = C(n,m) * t^(m) * (1 - t)^(n-m)
     *        C(n,m) = Combinations of n things, taken m at a time
     *               = n! / (m! * (n-m)!)
     * </pre>
     */
    public static final int SEG_QUADTO		= 2;

    /**
     * The segment type constant for the set of 3 points that specify
     * a cubic parametric curve to be drawn from the most recently
     * specified point.
     * The curve is interpolated by solving the parametric control
     * equation in the range <code>(t=[0..1])</code> using
     * the most recently specified (current) point (CP),
     * the first control point (P1),
     * the second control point (P2),
     * and the final interpolated control point (P3).
     * The parametric control equation for this curve is:
     * <pre>
     *          P(t) = B(3,0)*CP + B(3,1)*P1 + B(3,2)*P2 + B(3,3)*P3
     *          0 &lt;= t &lt;= 1
     *
     *        B(n,m) = mth coefficient of nth degree Bernstein polynomial
     *               = C(n,m) * t^(m) * (1 - t)^(n-m)
     *        C(n,m) = Combinations of n things, taken m at a time
     *               = n! / (m! * (n-m)!)
     * </pre>
     * This form of curve is commonly known as a B&eacute;zier curve.
     */
    public static final int SEG_CUBICTO		= 3;

    /**
     * The segment type constant that specifies that
     * the preceding subpath should be closed by appending a line segment
     * back to the point corresponding to the most recent SEG_MOVETO.
     */
    public static final int SEG_CLOSE		= 4;

    /**
     * Returns the winding rule for determining the interior of the
     * path.
     * @return the winding rule.
     * @see #WIND_EVEN_ODD
     * @see #WIND_NON_ZERO
     */
    public int getWindingRule();

    /**
     * Tests if the iteration is complete.
     * @return <code>true</code> if all the segments have 
     * been read; <code>false</code> otherwise.
     */
    public boolean isDone();

    /**
     * Moves the iterator to the next segment of the path forwards
     * along the primary direction of traversal as long as there are
     * more points in that direction.
     */
    public void next();

    /**
     * Returns the coordinates and type of the current path segment in
     * the iteration.
     * The return value is the path-segment type:
     * SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, or SEG_CLOSE.
     * A float array of length 6 must be passed in and can be used to
     * store the coordinates of the point(s).
     * Each point is stored as a pair of float x,y coordinates.
     * SEG_MOVETO and SEG_LINETO types returns one point,
     * SEG_QUADTO returns two points,
     * SEG_CUBICTO returns 3 points
     * and SEG_CLOSE does not return any points.
     * @param coords an array that holds the data returned from
     * this method
     * @return the path-segment type of the current path segment.
     * @see #SEG_MOVETO
     * @see #SEG_LINETO
     * @see #SEG_QUADTO
     * @see #SEG_CUBICTO
     * @see #SEG_CLOSE
     */
    public int currentSegment(float[] coords);

    /**
     * Returns the coordinates and type of the current path segment in
     * the iteration.
     * The return value is the path-segment type:
     * SEG_MOVETO, SEG_LINETO, SEG_QUADTO, SEG_CUBICTO, or SEG_CLOSE.
     * A double array of length 6 must be passed in and can be used to
     * store the coordinates of the point(s).
     * Each point is stored as a pair of double x,y coordinates.
     * SEG_MOVETO and SEG_LINETO types returns one point,
     * SEG_QUADTO returns two points,
     * SEG_CUBICTO returns 3 points
     * and SEG_CLOSE does not return any points.
     * @param coords an array that holds the data returned from
     * this method
     * @return the path-segment type of the current path segment.
     * @see #SEG_MOVETO
     * @see #SEG_LINETO
     * @see #SEG_QUADTO
     * @see #SEG_CUBICTO
     * @see #SEG_CLOSE
     */
    public int currentSegment(double[] coords);
}
