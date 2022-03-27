package ezl.core.geom

case class Polygon(pts : List[Vec2])

object Polygon {
  given Conversion[Polygon, List[Vec2]] with
    override def apply(x : Polygon) : List[Vec2] = x.pts

  given Conversion[List[Vec2], Polygon] with
    def apply(x : List[Vec2]) = Polygon(x)
}

