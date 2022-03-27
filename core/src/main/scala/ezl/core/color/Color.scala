package ezl.core.color

import java.awt.{Color => JColor}

case class Color(red : Double, green : Double, blue : Double, alpha : Double = 1.0)

object Color {
  given Conversion[JColor, Color] with
    def apply(c : JColor) = 
      Color(c.getRed()/256.0, c.getGreen()/256.0, c.getBlue()/256.0, c.getAlpha()/256.0)

  given Conversion[Color, JColor] with
    def apply(c : Color) = c match
      case Color(r, g, b, a) => 
        JColor(r.toFloat, g.toFloat, b.toFloat, a.toFloat)
}

trait ColorRepr[A] {
  def fromColor : Color => A
  def toColor : A => Color
}

object ColorRepr {

  def cc2i(x : Double) = Math.floor(x * 256).toInt

  given ColorRepr[Color] with {
    def fromColor = identity
    def toColor = identity
  }
}

case class HSB(hue : Double, sat : Double, bgt : Double) 

object HSB {
  import java.awt.Color.{RGBtoHSB, getHSBColor}
  import ColorRepr.cc2i

  given ColorRepr[HSB] with {
    def fromColor = {
      case Color(r, g, b, _) => {
        val hsb = RGBtoHSB(cc2i(r), cc2i(g), cc2i(b), null)
        HSB(hsb(0), hsb(1), hsb(2))
      }
    }

    def toColor = {
      case HSB(h, s, b) => getHSBColor(h.toFloat, s.toFloat, b.toFloat)
    }
  } 
}

private def fmod(a : Double, b : Double) = {
  val res = Math.floor(a / b)
  a - res * b
}

private def mod1(x : Double) = {
  if (x < 0) fmod(1-fmod(-x, 1), 1)
  else fmod(x, 1)
}

private def hsbInterpolate[C1, C2](c1 : C1, c2 : C2, t : Double)
(using _c1 : ColorRepr[C1], _c2 : ColorRepr[C2], _hsb : ColorRepr[HSB]): Color = {
  var ca = _hsb.fromColor(_c1.toColor(c1))
  var cb = _hsb.fromColor(_c2.toColor(c2))
  var ha = ca.hue
  var hb = cb.hue
  var d = hb - ha
  if (ha > hb) {
    hsbInterpolate[C2, C1](c2, c1, 1 - t)
  } else {
    val h : Double =
      if (d > 0.5) {
        ha = ha + 1
        mod1(ha + t * (hb - ha))
      } else ha + t * d

    _hsb.toColor(HSB(h, ca.sat * (1 - t) + cb.sat * t, ca.bgt * (1 - t) + cb.bgt * t))
  }
}

