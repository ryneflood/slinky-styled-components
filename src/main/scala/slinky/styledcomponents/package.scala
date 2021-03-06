package slinky

import slinky.core.{Attr, AttrPair}

import scala.scalajs.js
import js.JSConverters._

package object styledcomponents {
  implicit class StyledInterpolator(val sc: StringContext) extends AnyVal {
    def css[P](fns: InterpolationPart[P]*): InterpolatedCSS[P] = {
      InterpolatedCSS(sc.parts, fns)
    }

    def injectGlobal[P](fns: InterpolationPart[P]*): Unit = {
      StyledComponentsNamespace.injectGlobal.call(
        js.undefined,
        sc.parts.toJSArray +: fns: _*
      )
    }

    def keyframes[P](fns: InterpolationPart[P]*): KeyframesInterpolationPart[P] = {
      StyledComponentsNamespace.keyframes.call(
        js.undefined,
        sc.parts.toJSArray +: fns: _*
      ).asInstanceOf[KeyframesInterpolationPart[P]]
    }
  }

  object innerRef extends Attr {
    @inline def :=(v: org.scalajs.dom.Element => Unit) = new AttrPair[Any]("innerRef", v)
    @inline def :=(v: slinky.core.facade.ReactRef[org.scalajs.dom.Element]) = new AttrPair[Any]("innerRef", v)
  }
}
