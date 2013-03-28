package org.fleen.core.grammar;

import java.util.ArrayList;
import java.util.List;

/*
 * 2D MATH
 * 
 * We use 2 basic geometry systems in this latest version of the fleen
 * --The first system is constrained to 24 directions. We call it D24
 * That is, all segments are parallel to one of 12 axii. The direction from one point to it's 
 * adjacent (in a strand or loop) is always one of our 24 directions.
 * This graphically unifies and enhances as well as simplifying our task of geometry creation.
 * --The second system is the plain ol 2d plane. After we have built a form in D24 we condition it's strands, smoothing
 * curves and/or whatever.
 * Herein are contained various constants and primitive functions for handling geometry in both of our systems. 
 */
public class Math2D{
  
  ///////////////////////
  //################---//
  // STANDARD GEOMETRY //
  //################---//
  ///////////////////////
  
  
  //################
  // CONSTANTS
  //################
  
  public static final double 
    PI=3.14159265358979323846,
    SQRT2=sqrt(2.0),
    SQRT3=sqrt(3.0),
    GOLDEN_RATIO=1.618033988749894;//(1.0+sqrt(5.0))/2.0;
  
  //################
  // MISC
  //################
  
  public static int abs(int a){
    return (a<0)?-a:a;}
  
  public static double abs(double a){
    return (a<0)?-a:a;}
  
  public static double sqrt(double d){
    return StrictMath.sqrt(d);}
  
  /*
   * Returns the factors of the specified integer starting at 1 and ending at the integer itself.
   * If only 2 are returned then it's prime.
   * If only one is returned then i=1 
   */
  public static List<Integer> getFactors(int i){
    List<Integer> factors=new ArrayList<Integer>();
    for(int factor=1;factor<=i;factor++){
      if(i%factor==0){
        factors.add(new Integer(factor));}}
    return factors;}
  
  //################
  // TRIGONOMETRY FUNCTIONS
  //################
  
  public static double sin(double a) {
    return StrictMath.sin(a);}
  
  public static double cos(double a) {
    return StrictMath.cos(a);}
  
  public static double tan(double a) {
    return StrictMath.tan(a);}
  
  public static double atan2(double x,double y){
    return StrictMath.atan2(x,y);}
  
  //################
  // GET DISTANCE
  //################
  
  public static double getDistance_2Points(double p0x,double p0y,double p1x,double p1y){
    p0x-=p1x;
    p0y-=p1y;
    return sqrt(p0x*p0x+p0y*p0y);}
  
  public static double getDistance_PointLine(double px,double py,double lx0,double ly0,
    double lx1,double ly1){
    lx1-=lx0;
    ly1-=ly0;
    px-=lx0;
    py-=ly0;
    double dotprod=px*lx1+py*ly1;
    double projlenSq=dotprod*dotprod/(lx1*lx1+ly1*ly1);
    double lenSq=px*px+py*py-projlenSq;
    if(lenSq<0)lenSq=0;
    return sqrt(lenSq);}

  //TODO
  public static double getDistance_PointRay(double px,double py,double rpx,double rpy,double rd){
    throw new UnsupportedOperationException();}
  
//  public static double getDistance_PointSeg(
//    double px,double py,double sp0x,double sp0y,double sp1x,double sp1y){
//    return java.awt.geom.Line2D.ptSegDist(sp0x,sp0y,sp1x,sp1y,px,py);}
  
  //TODO
  public static double getDistance_2Lines(double l0p0x,double l0p0y,double l0p1x,double l0p1y,
    double l1p0x,double l1p0y,double l1p1x,double l1p1y){
    throw new UnsupportedOperationException();}
  
  //TODO
  public static double getDistance_2Segs(double s0p0x,double s0p0y,double s0p1x,double s0p1y,
    double s1p0x,double s1p0y,double s1p1x,double s1p1y){
    throw new UnsupportedOperationException();}
  
  //################
  // GET DIRECTION OR ANGLE
  //################
  
  //Returns the direction of p1 relative to p0
  //range of possible directions is [-PI,PI)
  public static double getDirection_PointPoint(double p0x,double p0y,double p1x,double p1y){
    p1x-=p0x;
    p1y-=p0y;
    return Math.atan2(p1x,p1y);}
  
  /*
   * normalize direction value
   * returns direction value in the range [0,2PI]
   */
  public static double normalizeDirection(double a){
    //reduce giant values
    a=a%(2.0*Math.PI);
    //fix negatives
    if(a<0){a=(2.0*Math.PI)+a;}
    return a;}
  
//  /**
//   * Returns the direction of p1 relative to p0
//   * range of possible directions is [-PI,PI)
//   */
//  public static double getDirection_2Points(double p0x,double p0y,double p1x,double p1y){
//    p1x-=p0x;
//    p1y-=p0y;
//    return Math.atan2(p1x,p1y);}
  
  /**
   * Returns the direction of p1 relative to p0
   * range of possible directions is [0,2PI)
   */
  public static double getDirection_2Points(double p0x,double p0y,double p1x,double p1y){
    p1x-=p0x;
    p1y-=p0y;
    double d=Math.atan2(p1x,p1y);
    if(d<0)d=(2.0*PI)+d;
    return d;}
  
  /**
   * Consider the sequence of points going from roughly south to north: p0,p1,p2
   * Consider the angle on the right.
   * We return the direction (D) in the center of that angle, pointing right.
   *     P2 o
   *       /
   *      / 
   *  P1 o  ---> D
   *      \
   *       \
   *    P0  o
   * 
   */
  public static final double getDirection_3Points(double p0x,double p0y,double p1x,
    double p1y,double p2x,double p2y){
    double d0=getDirection_2Points(p1x,p1y,p2x,p2y);
    double d1=getDirection_2Points(p1x,p1y,p0x,p0y);
    return getDirection_2Directions(d0,d1);}
  
  /**
   * Consider the points : p0,p1,p2
   * Traversing them clockwise in numeric sequence, the angle is on the right.
   */
  public static double getAngle_3Points(double p0x,double p0y,double p1x,double p1y,
    double p2x,double p2y){
    //get the angle between the 2 directions
    double d0=getDirection_2Points(p1x,p1y,p2x,p2y);
    double d1=getDirection_2Points(p1x,p1y,p0x,p0y);
    return getAngle_2Directions(d0,d1);}
  
  /**
   * Consider the arc described when traversing the arc clockwise from direction d0 to d1.
   * We return the direction in the center of that arc.
   */
  public static double getDirection_2Directions(double d0,double d1){
    double d=0;
    if(d0<=d1){
      d=d0+((d1-d0)/2.0);
    }else{
      double span=(Math.PI*2.0)-(d0-d1);
      d=normalizeDirection(d0+(span/2.0));}
   return d;}
  
  public static void TEST_getDirection_2Directions(){
    System.out.println("---TEST_getDirection_2Directions---");
    double d0=PI*0.5;
    double d1=PI;
    double d2=getDirection_2Directions(d0,d1);
    System.out.println("TEST1:"+d2);
    d2=getDirection_2Directions(d1,d0);
    System.out.println("TEST2:"+d2);
    System.out.println("----------");}
  
  /**
   * The angle spanned by 2 directions.
   * Returns the angle spanned when we traverse the arc clockwise from d0 to d1.
   * @param d0 The first direction.
   * @param d1 The second direction.
   * @return the angle spanned.
   */
  public static final double getAngle_2Directions(double d0,double d1){
    double span;
    if(d0==d1){
      span=0;
    }else{
      if(d0<d1){
        span=d1-d0;
      }else{
        span=(Math.PI*2.0)-d0+d1;}}
    return span;}
  
  public static final double getDirectionalDeviation_3Points(
    double p0x,double p0y,double p1x,double p1y,double p2x,double p2y){
    double d0=getDirection_2Points(p0x,p0y,p1x,p1y),d1=getDirection_2Points(p1x,p1y,p2x,p2y);
    double d=getDeviation_2Directions(d0,d1);
    return d;}
  
  /**
   * Returns the angular degree to which d1 deviates from d0.
   * If the d1=d0 then we return 0.
   * If d1 is counterclockwise to d0 then we return a negative value.
   * If d1 is clockwise to d0 then we return a positive value.
   * If d1 is the opposite of d0 then we return PI.
   * Returned value ranges [-PI,PI].
   * ex: a direction perpendicular to this one would return -PI/2.0 or PI/2.0.
   */
  public static final double getDeviation_2Directions(double d0,double d1){
    d0=normalizeDirection(d0);
    d1=normalizeDirection(d1);
    double s=getAngle_2Directions(d0,d1);
    double dev;
    if(d0==d1){
      dev=0;}
    if(s<Math.PI){
      dev=s;
    }else{
      dev=-((Math.PI*2.0)-s);}
    return dev;}
  
  /*
   * The absolute value of getDeviation
   */
  public static final double getDifference_2Directions(double d0,double d1){
    return abs(getDeviation_2Directions(d0,d1));}
  
//  /**
//   * Returns the magnitude of difference between the specified directions.
//   */
//  public static double getDirectionDifference(double d0,double d1){
//    double a0=Math.abs(d0-d1);
//    double a1=(Math.PI*2.0)-a0;
//    double d=Math.min(a0,a1);
//    return d;}
//  
//  /**
//   * Returns the direction of d1 relative to d0.
//   * It returns an angle offset in the range [PI,-PI].
//   * If d1 is clockwise of d0 a positive value is returned, counterclockwise returns a negative.
//   * if d0==d1 then 0 is returned.
//   * d0 and d1 need to be in the normal range [0,2PI] for a valid value to be returned.
//   * We do a clever thing.
//   * We subtract d0 from d1 and normalize the result. So it's like d0 is 0. So it's easy.
//   */
//  public static double getRelativeDirection(double d0,double d1){
//    double dC=normalizeDirection(d1-d0);
//    double r;
//    if(dC<Math.PI){
//      r=dC; 
//    }else{
//      r=-((Math.PI*2.0)-dC);}
//    return r;}
  
//  public static double getDirection_PointSeg(
//    double px,double py,double sp0x,double sp0y,double sp1x,double sp1y){
//    Point2D.Double.
//    
//  }
  
  //################
  // GET POINT
  //################
  
  /**
   * @param x point x
   * @param y point y
   * @param d offset direction
   * @param i offset interval
   */
  public static final double[] getPoint_PointDirectionInterval(double x,double y,double d,double i){
    double oY=i*Math.cos(d);
    double oX=i*Math.sin(d);
    double[] p={x+oX,y+oY};
    return p;}
  
  /**
   * returns a point between p0 and p1 at i distance from p0 in the direction of p1
   * @param p0x point0 x
   * @param p0y point0 y
   * @param p1x point1 x
   * @param p1y point1 y
   * @param i offset interval
   */
  public static final double[] getPoint_PointPointInterval(
    double p0x,double p0y,double p1x,double p1y,double i){
    double d=getDirection_PointPoint(p0x,p0y,p1x,p1y);
    double oY=i*Math.cos(d);
    double oX=i*Math.sin(d);
    double[] p={p0x+oX,p0y+oY};
    return p;}
  
  //flat up hexagon vertex directions
  public static final double[]
    FUH_VDIR={11.0/6.0*PI,1.0/6.0*PI,3.0/6.0*PI,5.0/6.0*PI,7.0/6.0*PI,9.0/6.0*PI};
  
  public static double[] getPoint_Mid2Points(double p0x,double p0y,double p1x,double p1y){
    p0x=(p0x+p1x)/2.0;
    p0y=(p0y+p1y)/2.0;
    return new double[]{p0x,p0y};}
  
  /**
   * Gets point on line l closest to point p
   * this may be screwy, don't trust it
   * TODO optimize
   */
  
  public static final double[] getPoint_ClosestOnSegToPoint(double px,double py,double sp0x,double sp0y,
    double sp1x,double sp1y){
    double x=getAngle_3Points(px,py,sp0x,sp0y,sp1x,sp1y);
    double y=PI/2.0;
    double z=PI-x-y;
    double F=getDistance_2Points(sp0x,sp0y,px,py);
    double G=F*sin(z);
    double d=getDirection_PointPoint(sp0x,sp0y,sp1x,sp1y);
    double[] p=getPoint_PointDirectionInterval(sp0x,sp0y,d,G);
    return p;}
  
  
//  public static double[] getPoint_ClosestOnLineToPoint(double px,double py,double lp0x,double lp0y,
//    double lp1x,double lp1y){
//    //distance from p to l
//    double da=getDistance_PointLine(px,py,lp0x,lp0y,lp1x,lp1y);
//    //if the point is on the line then return it
//    if(da==0)return new double[]{px,py};
//    //distance from our point to an arbitrary point on the line (lp0)
//    double db=getDistance_2Points(px,py,lp0x,lp0y);
//    //if lp0 is that closest point then return it
//    if(da==db)return new double[]{lp0x,lp0y};
//    //distance from lp0 to our new point
//    double d2=Math.sqrt(db*db-da*da);
//    //get 2 points on line at db distance in the 2 directions from al. 
//    //Return the one closer to p.
//    double d0=getDirection_2Points(lp0x,lp0y,lp1x,lp1y);
//    double d1=getDirection_2Points(lp1x,lp1y,lp0x,lp0y);
//    double[] p0=getPoint_PointDirectionInterval(lp0x,lp0y,d0,db);
//    double[] p1=getPoint_PointDirectionInterval(lp0x,lp0y,d1,db);
//    if(getDistance_2Points(px,py,p0[0],p0[1])<getDistance_2Points(px,py,p1[0],p1[1])){
//      return p0;
//    }else{
//      return p1;}}
         
//  public static Point getPoint_Centroid(Collection<? extends Point> points){
//    int s=points.size();
//    //if there's only one point in the list then return it
//    if(s==1)
//      return points.iterator().next();
//    //get that centroid
//    double x=0;
//    double y=0;
//    Iterator<? extends Point> i=points.iterator();
//    Point p;
//    while(i.hasNext()){
//      p=(Point)i.next();
//      x+=p.x;
//      y+=p.y;}
//    y/=s;
//    x/=s;
//    return new Point(x,y);}
  
  /**
   * Returns a point on the seg p0(x0,y0)-p1(x1,y1)
   * Bias of 0.5 is exactly between them. 
   * Less than 0.5 is closer to p0, Greater is closer to p1.
   * A bias less than 0 or greater than 1 returns an error
   */
  public static final double[] getPoint_Between2Points(double x0,double y0,double x1,double y1,double bias){
    if(bias<0||bias>1.0)
      throw new IllegalArgumentException("INVALID BIAS VALUE! bias="+bias+". Valid range is [0,1]");
    double x=x0*(1.0-bias)+x1*bias;
    double y=y0*(1.0-bias)+y1*bias;
    return new double[]{x,y};}
  
  //################
  // GET ANGLE
  //################
  
  //################
  // GET INTERSECTION
  //################
  
  public static final double[] getIntersection_2Lines(double l0x0,double l0y0,double l0x1,double l0y1,
    double l1x0,double l1y0,double l1x1,double l1y1){
    double a1,b1,c1,//constants of linear equations
    a2,b2,c2,
    det_inv,//the inverse of the determinant of the coefficient matrix
    m1,m2;//slopes
    //compute slopes, note the fudged infinity. it works
    if((l0x1-l0x0)!=0){
      m1=(l0y1-l0y0)/(l0x1-l0x0);
    }else{
      m1=Double.MAX_VALUE/4.0;}//close enough to infinity. we use 4 cuz 2 raises errors
    if((l1x1-l1x0)!=0){
      m2=(l1y1-l1y0)/(l1x1-l1x0);
    }else{
      m2=Double.MAX_VALUE/4.0;}
    //compute constants
    a1=m1;
    a2=m2;
    b1=-1;
    b2=-1;
    c1=(l0y0-m1*l0x0);
    c2=(l1y0-m2*l1x0);
    //compute the inverse of the determinate
    det_inv=1.0/(a1*b2-a2*b1);
    //use Kramers rule to compute xi and yi
    double xi=((b1*c2-b2*c1)*det_inv);
    double yi=((a2*c1-a1*c2)*det_inv);
    return new double[]{xi,yi};}
  
  /*
   * this works just fine
   * make 2 lines
   * get the intersection of the lines
   * test the direction of the intersection, does it lay in the ray-directions from
   * the ray-points?
   */
  public static double[] getIntersection_2Rays(double r0x,double r0y,
    double r0d,double r1x,double r1y,double r1d){
    //make lines by creating a second point for each of our ray points
    double[] p0=getPoint_PointDirectionInterval(r0x,r0y,r0d,1.0);
    double[] p1=getPoint_PointDirectionInterval(r1x,r1y,r1d,1.0);
    //get the intersection of those lines
    double[] i=getIntersection_2Lines(r0x,r0y,p0[0],p0[1],r1x,r1y,p1[0],p1[1]);
    //if they intersected, does the intersection lay in the proper direction?
    //we test directions by close-enoughness
    if(i!=null){
      double d0=getDirection_PointPoint(r0x,r0y,i[0],i[1]);
      double d1=getDirection_PointPoint(r1x,r1y,i[0],i[1]);
      double diff0=getDifference_2Directions(d0,r0d);
      double diff1=getDifference_2Directions(d1,r1d);
      if(!(diff0<1.0&&diff1<1.0))//1 works fine. Should we use PI/2? Does it matter at all?
        i=null;}
    return i;}
  
  /**
   * If there's an intersection it returns 2 points, if there isn't it returns null.
   * Returns 2 intersection points {x0,y0,x1,y1}
   * Got it from a reputable coder. It works fine.
   */
  public static double[] getIntersection_2Circles(double c0x,double c0y,double c0r,
    double c1x,double c1y,double c1r){
    double a, dx, dy, d, h, rx, ry;
    double p2x, p2y;
    //dx and dy are the vertical and horizontal distances between the circle centers.
    dx=c1x-c0x;
    dy=c1y-c0y;
    //Determine the straight-line distance between the centers.
    d=sqrt((dy*dy)+(dx*dx));
    //nonintersection, the discs don't touch
    if(d>(c0r+c1r))return null;
    //nonintersection, one circle contains the other
    if(d<abs(c0r-c1r))return null;
    //'p2' is the point where the line through the circle
    //intersection points crosses the line between the circle centers.  
    //Determine the distance from point 0 to point 2.
    a=((c0r*c0r)-(c1r*c1r)+(d*d))/(2.0*d);
    //Determine the coordinates of point 2.
    p2x=c0x+(dx*a/d);
    p2y=c0y+(dy*a/d);
    //Determine the distance from point 2 to either of the intersection points.
    h=sqrt((c0r*c0r)-(a*a));
    //Now determine the offsets of the intersection points from point 2.
    rx=-dy*(h/d);
    ry=dx*(h/d);
    /* Determine the absolute intersection points. */
    double i0x=p2x+rx;
    double i0y=p2y+ry;
    double i1x=p2x-rx;
    double i1y=p2y-ry;
    return new double[]{i0x,i0y,i1x,i1y};}
  
  public static final double getSignedArea2D(double[][] p){
    double signedarea2d=0.0;
    int i0,s=p.length;
    for(int i=0;i<s;i++){
      i0=i+1;
      if(i0==s)i0=0;
      signedarea2d=signedarea2d+(p[i][0]*p[i0][1])-(p[i][1]*p[i0][0]);}
    //
    signedarea2d*=0.5;
    return signedarea2d;}
  
  public static final double getArea_Triangle(double[] p0,double[] p1,double[] p2){
    double a=Math.abs(
      (p0[0]*(p1[1]-p2[1])+p1[0]*(p2[1]-p0[1])+p2[0]*(p0[1]-p1[1]))/2);
    return a;}

}
