package ezl.core.geom

import ezl.core._

case class Vec2(x : Num, y : Num) { self =>

  def +(that : Vec2) : Vec2 = {
    Vec2(self.x + that.x, self.y + that.y)
  }

  def -(that : Vec2) : Vec2 = {
    Vec2(self.x - that.x, self.y - that.y)
  }
  
  def *(factor : Num) : Vec2 = {
    Vec2(self.x * factor, self.y * factor)
  }

  def unary_- : Vec2 = {
    Vec2(-self.x, -self.y)
  }

  def *(that : Vec2) : Num = {
    self.x * that.x + self.y * that.y
  }

  def <*(A : Mat2) = A *> self
}

object Vec2 {
  def zero = Vec2(0, 0)
}

/* Matrix of [a, b; c, d] */

case class Mat2(a : Num, b : Num, c : Num, d : Num) { self =>
  def +(that : Mat2) : Mat2 = {
    Mat2(a + that.a, b + that.b, c + that.c, d + that.d)
  }

  def -(that : Mat2) : Mat2 = {
    Mat2(a - that.a, b - that.b, c - that.c, d - that.d)
  }

  def *(lam : Num) : Mat2 = {
    Mat2(a * lam, b * lam, c * lam, d * lam)
  }

  def *(that : Mat2) : Mat2 = {
    Mat2(a * that.a + b * that.c, a * that.b + b * that.d, 
         c * that.a + d * that.c, c * that.b + d * that.d)
  }

  def unary_-(that : Mat2) : Mat2 = {
    Mat2(-a, -b, -c, -d)
  }

  def *>(v : Vec2) : Vec2 = {
    Vec2(a * v.x + b * v.y, c * v.x + d * v.y)
  }
}

object Mat2 {
  val Id = Mat2(1.0, 0.0, 0.0, 1.0)
}
