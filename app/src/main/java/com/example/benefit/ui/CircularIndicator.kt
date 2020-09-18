//package com.example.benefit.ui//
////  CircularIndicator.swift
////  Benefit
////
////  Created by Doni on 7/14/20.
////  Copyright © 2020 Shin Anatoly. All rights reserved.
////
//
//
//import android.content.Context
//import android.graphics.Color
//import android.util.AttributeSet
//import android.view.View
//
//class GradientCircularProgressBar(ctx: Context, attrs: AttributeSet) : View(ctx, attrs) {
//    var color: Color? = Color()
//        /*.valueOf(0.9098039216F, 0.9098039216F, 0.9098039216F, 1F) *//*Color.rgb()*/
//        set(value) {
//            color = value
//            requestLayout()
//        }
//
//    //  {
////        didSet { setNeedsDisplay() }
////    }
//
//    var ringWidth = 37F
//        set(value) {
//            ringWidth = value
//            requestLayout()
//        }
//
//    var knobDiameter = 40.0
//        set(value) {
//            knobDiameter = value
//            requestLayout()
//        }
//
//    var progress =  0F
//        set(value) {
//            progress = value
//            requestLayout()
//        }
//
//
//    private var progressLayer = CAShapeLayer()
//    private var backgroundLayer = CAShapeLayer()
//    private const gradientLayer = CAGradientLayer()
//    private const finishCircle = CAShapeLayer()
//    private const knob = CAShapeLayer()
//    private const innerCircle = CAShapeLayer()
//    private const centralCircle = CAShapeLayer()
//
////    override init(frame: CGRect)
////    {
////        super.init(frame: frame)
////        setupLayers()
////        //createAnimation()
////    }
////
////    required init?(coder: NSCoder)
////    {
////        super.init(coder: coder)
////        setupLayers()
////        //createAnimation()
////    }
////
////    private func setupLayers()
////    {
////        /* background layer */
////        backgroundLayer.lineWidth = ringWidth
////        backgroundLayer.fillColor = nil
////        backgroundLayer.strokeColor = color?.cgColor ?? UIColor.lightGray.cgColor
////        //layer.mask = backgroundMask
////        layer.addSublayer(backgroundLayer)
////
//////        backgroundLayer.shadowRadius = 8
//////        backgroundLayer.shadowOffset = CGSize(width: 0, height: 2)
//////        backgroundLayer.shadowOpacity = 0.2
////
////
////        /* progress mask */
////        progressLayer.lineWidth = ringWidth
////        progressLayer.fillColor = nil
////
////        /* gradient progress*/
////        gradientLayer.type = . conic
////
////                gradientLayer.colors = [
////            UIColor(red: 0.757, green: 0.396, blue: 0.867, alpha: 1).cgColor,
////        UIColor(red: 0.757, green: 0.396, blue: 0.867, alpha: 1).cgColor,
////        UIColor(red: 0.506, green: 0.357, blue: 0.962, alpha: 1).cgColor,
////        UIColor(red: 0.506, green: 0.357, blue: 0.962, alpha: 1).cgColor,
////        UIColor.yellow.cgColor,
////        UIColor.red.cgColor
////        ]
////
////        gradientLayer.startPoint = CGPoint(x: 0.5, y: 0.5)
////        const endY = - 0.058 + frame.size.width / frame.size.height / 2
////        gradientLayer.endPoint = CGPoint(x: 1, y: endY)
////
////        layer.addSublayer(gradientLayer)
////        gradientLayer.transform = CATransform3DMakeRotation(CGFloat(90 * Double.pi / 180), 0, 0, -1)
////
////        //progressLayer.transform = CATransform3DMakeRotation(CGFloat(Double.pi * 0.75), 0, 0, -1)
////        gradientLayer.mask = progressLayer
////
////        /* finish circle */
////        finishCircle.fillColor = color?.cgColor//color.cgColor
////        finishCircle.strokeColor = color?.cgColor
////        finishCircle.lineWidth = 0
////        finishCircle.strokeStart = 0
////        finishCircle.strokeEnd = 0.5
//////        finishCircle.shadowRadius = 4
//////        finishCircle.shadowOffset = CGSize(width: 6, height: 0)
//////        finishCircle.shadowOpacity = 0.15
////
////        layer.addSublayer(finishCircle)
////
////        /* inner circle */
////        innerCircle.lineWidth = 9
////        innerCircle.fillColor = nil
////        innerCircle.strokeColor =
////        # colorLiteral(red: 0.8117647059, green: 0.8117647059, blue: 0.8117647059, alpha: 1).cgColor
////
////        layer.addSublayer(innerCircle)
////
////        /* central circle */
////        centralCircle.lineWidth = 1
////        centralCircle.fillColor =
////        # colorLiteral(red: 0.9646351933, green: 0.9647472501, blue: 0.9645835757, alpha: 1).cgColor
////        centralCircle.strokeColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.03).cgColor
////
////
////
////        layer.addSublayer(centralCircle)
////
////        /* round knob */
////        knob.lineCap = . round
////                knob.strokeStart = 0
////        knob.strokeEnd = 1
////        knob.lineWidth = 3
////        knob.strokeColor = UIColor.white.cgColor
////        knob.fillColor = nil
////
////        knob.shadowRadius = 3
////        knob.shadowColor = UIColor.black.cgColor
////        knob.shadowOpacity = 0.3
////        knob.shadowOffset = CGSize(width: - 1, height: 1)
////        layer.addSublayer(knob)
////
////
////        //gradientLayer.locations = [0.35, 0.5, 0.65]
////    }
////
////
////    private func createAnimation()
////    {
////        const startPointAnimation = CAKeyframeAnimation (keyPath: "startPoint")
////        startPointAnimation.values = [CGPoint.zero, CGPoint(x: 1, y: 0), CGPoint(x: 1, y: 1)]
////
////        startPointAnimation.repeatCount = Float.infinity
////        startPointAnimation.duration = 1
////
////        const endPointAnimation = CAKeyframeAnimation (keyPath: "endPoint")
////        endPointAnimation.values = [CGPoint(x: 1, y: 1), CGPoint(x: 0, y: 1), CGPoint.zero]
////
////        endPointAnimation.repeatCount = Float.infinity
////        endPointAnimation.duration = 1
////
////
////        gradientLayer.add(startPointAnimation, forKey: "startPointAnimation")
////        gradientLayer.add(endPointAnimation, forKey: "endPointAnimation")
////    }
////
////
////    override func draw(_ rect: CGRect)
////    {
////
////        const circlePath = UIBezierPath (ovalIn: rect.insetBy(dx: ringWidth / 2, dy: ringWidth / 2))
////        backgroundLayer.path = circlePath.cgPath
////
////        progressLayer.path = circlePath.cgPath
////        progressLayer.lineCap = . round
////                progressLayer.strokeStart = 0
////        progressLayer.strokeEnd = progress
////        progressLayer.strokeColor = UIColor.black.cgColor
////
////        gradientLayer.frame = rect
////
////        const ar = Double ((rect.width / 2)) - 37 / 2
////        const ax = ar * cos ((1.49 * Double.pi + Double.pi * Double(2)))
////        const ay = ar * sin ((1.49 * Double.pi + Double.pi * Double(2)))
////
////        finishCircle.path =
////            UIBezierPath(ovalIn: CGRect(x: ax +ar, y: ay +ar, width: 37, height: 37)).cgPath
////
////        const innerCirclePath = UIBezierPath (ovalIn: circlePath.bounds.insetBy(dx: 28, dy: 28))
////        innerCircle.path = innerCirclePath.cgPath
////
////        const centralCirclePath = UIBezierPath (ovalIn: innerCirclePath.bounds.insetBy(dx: 10, dy: 10))
////        centralCircle.path = centralCirclePath.cgPath
////
////
////
////        const r = Double ((rect.width / 2)) - (knobDiameter / 2 - 1)
////        const x = r * cos ((2 * Double.pi + Double.pi * Double((progress - 0.25) * 2)))
////        const y = r * sin ((2 * Double.pi + Double.pi * Double((progress - 0.25) * 2)))
////
////        const knobPath = UIBezierPath (ovalIn: CGRect(x: x+r, y: y+r, width: knobDiameter, height: knobDiameter))
////        knob.path = knobPath.cgPath
////
////        if progress == 1 {
////            knob.fillColor = UIColor.orange.cgColor
////        }
////
////        bringSubviewToFront(subviews[0])
////    }
//}
