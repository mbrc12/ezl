package ezl.core

def fmod(a : Double, b : Double) = {
  val res = Math.floor(a / b)
  a - res * b
}

def mod1(x : Double) = {
  if (x < 0) fmod(1-fmod(-x, 1), 1)
  else fmod(x, 1)
}

