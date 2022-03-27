package ezl.core.canvas

import ezl.core._

import ezl.core.geom._
import ezl.core.color._

/* Typeclass for Canvases */

trait CanvasOps[A] {

  type V = Vec2
  
  extension(self : A) {

    def stroke : Brush
    def stroke_= (brush : Brush) : A 

    def fill : Brush
    def fill_= (brush : Brush) : A

    def point(a : V) : A 

    def updateStroke(stroke : Brush) : A

    /* this embeds a canvas into this canvas using the mapper to map points,
     * and the blender to blend colors of this canvas with the new canvas
     */
    def embed[B : CanvasOps](canvas : B, mapper : Mapper) : A 

    def segment(a : V, b : V) : A

    def polygon(pts : Polygon) : A = {

      assert(pts.length >= 1, 
        "Polygon must have atleast 1 points")

      (pts zip (pts.tail)).foldLeft(self) {
        (cur, pair) => cur.segment.tupled(pair)
      }
    }

    def triangle(a : V, b : V, c : V) : A = {
      self.polygon(List(a, b, c))
    }

    def rectangle(a : V, b : V, c : V, d : V) : A = {
      self.polygon(List(a, b, c, d))
    }

    def updateFill(fill : Brush) : A

    def filledPolygon(pts : List[V]) : A

    def filledTriangle(a : V, b : V, c : V) : A = {
      self.filledPolygon(List(a, b, c))
    }

    def filledRectangle(a : V, b : V, c : V, d : V) : A = {
      self.filledPolygon(List(a, b, c, d))
    }
  }
}

