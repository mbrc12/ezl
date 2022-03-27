package ezl.core.geom

/* Maps v -> target + A(v - source), so that source -> target, and everything
 * around is mapped linearly.
 */
case class Mapper(source : Vec2, target : Vec2, transform : Mat2 = Mat2.Id) {
  def apply(v : Vec2) = target + transform *> (v - source)
} 

object Mappers {
  val Id = Mapper(Vec2.zero, Vec2.zero, Mat2.Id) 
}
