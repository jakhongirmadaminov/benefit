//
//  CircularIndicator.swift
//  Benefit
//
//  Created by Doni on 7/14/20.
//  Copyright © 2020 Shin Anatoly. All rights reserved.
//


import Foundation
import UIKit

@available(iOS 12.0, *)
//@IBDesignable
class com.example.benefit.ui.GradientCircularProgressBar: UIView {
    @IBInspectable var color: UIColor? = #colorLiteral(red: 0.9098039216, green: 0.9098039216, blue: 0.9098039216, alpha: 1) {
        didSet { setNeedsDisplay() }
    }
    
    @IBInspectable var ringWidth: CGFloat = 37 {
        didSet { setNeedsDisplay() }
    }
    @IBInspectable var knobDiameter: Double = 40 {
        didSet { setNeedsDisplay() }
    }

    @IBInspectable
    var progress: CGFloat = 0 {
        didSet { setNeedsDisplay() }
    }
    
    private var progressLayer = CAShapeLayer()
    private var backgroundLayer = CAShapeLayer()
    private let gradientLayer = CAGradientLayer()
    private let finishCircle = CAShapeLayer()
    private let knob = CAShapeLayer()
    private let innerCircle = CAShapeLayer()
    private let centralCircle = CAShapeLayer()
    

    override init(frame: CGRect) {
        super.init(frame: frame)
        setupLayers()
        //createAnimation()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setupLayers()
        //createAnimation()
    }
    
    private func setupLayers() {
        /* background layer */
        backgroundLayer.lineWidth = ringWidth
        backgroundLayer.fillColor = nil
        backgroundLayer.strokeColor = color?.cgColor ?? UIColor.lightGray.cgColor
        //layer.mask = backgroundMask
        layer.addSublayer(backgroundLayer)
        
//        backgroundLayer.shadowRadius = 8
//        backgroundLayer.shadowOffset = CGSize(width: 0, height: 2)
//        backgroundLayer.shadowOpacity = 0.2
        
        
        /* progress mask */
        progressLayer.lineWidth = ringWidth
        progressLayer.fillColor = nil
        
        /* gradient progress*/
        gradientLayer.type = .conic
        
        gradientLayer.colors = [
            UIColor(red: 0.757, green: 0.396, blue: 0.867, alpha: 1).cgColor,
            UIColor(red: 0.757, green: 0.396, blue: 0.867, alpha: 1).cgColor,
            UIColor(red: 0.506, green: 0.357, blue: 0.962, alpha: 1).cgColor,
            UIColor(red: 0.506, green: 0.357, blue: 0.962, alpha: 1).cgColor,
            UIColor.yellow.cgColor,
            UIColor.red.cgColor
        ]
        
        gradientLayer.startPoint = CGPoint(x: 0.5, y: 0.5)
        let endY = -0.058 + frame.size.width / frame.size.height / 2
        gradientLayer.endPoint = CGPoint(x: 1, y: endY)
        
        layer.addSublayer(gradientLayer)
        gradientLayer.transform = CATransform3DMakeRotation(CGFloat(90 * Double.pi / 180), 0, 0, -1)

        //progressLayer.transform = CATransform3DMakeRotation(CGFloat(Double.pi * 0.75), 0, 0, -1)
        gradientLayer.mask = progressLayer
        
        /* finish circle */
        finishCircle.fillColor = color?.cgColor//color.cgColor
        finishCircle.strokeColor = color?.cgColor
        finishCircle.lineWidth = 0
        finishCircle.strokeStart = 0
        finishCircle.strokeEnd = 0.5
//        finishCircle.shadowRadius = 4
//        finishCircle.shadowOffset = CGSize(width: 6, height: 0)
//        finishCircle.shadowOpacity = 0.15
        
        layer.addSublayer(finishCircle)
        
        /* inner circle */
        innerCircle.lineWidth = 9
        innerCircle.fillColor = nil
        innerCircle.strokeColor = #colorLiteral(red: 0.8117647059, green: 0.8117647059, blue: 0.8117647059, alpha: 1).cgColor
        
        layer.addSublayer(innerCircle)
        
        /* central circle */
        centralCircle.lineWidth = 1
        centralCircle.fillColor = #colorLiteral(red: 0.9646351933, green: 0.9647472501, blue: 0.9645835757, alpha: 1).cgColor
        centralCircle.strokeColor = UIColor(red: 0, green: 0, blue: 0, alpha: 0.03).cgColor
        

        
        layer.addSublayer(centralCircle)
        
        /* round knob */
        knob.lineCap = .round
        knob.strokeStart = 0
        knob.strokeEnd = 1
        knob.lineWidth = 3
        knob.strokeColor = UIColor.white.cgColor
        knob.fillColor = nil
        
        knob.shadowRadius = 3
        knob.shadowColor = UIColor.black.cgColor
        knob.shadowOpacity = 0.3
        knob.shadowOffset = CGSize(width: -1, height: 1)
        layer.addSublayer(knob)
        
        
        //gradientLayer.locations = [0.35, 0.5, 0.65]
    }
    
    
    private func createAnimation() {
        let startPointAnimation = CAKeyframeAnimation(keyPath: "startPoint")
        startPointAnimation.values = [CGPoint.zero, CGPoint(x: 1, y: 0), CGPoint(x: 1, y: 1)]
        
        startPointAnimation.repeatCount = Float.infinity
        startPointAnimation.duration = 1
        
        let endPointAnimation = CAKeyframeAnimation(keyPath: "endPoint")
        endPointAnimation.values = [CGPoint(x: 1, y: 1), CGPoint(x: 0, y: 1), CGPoint.zero]

        endPointAnimation.repeatCount = Float.infinity
        endPointAnimation.duration = 1

                
        gradientLayer.add(startPointAnimation, forKey: "startPointAnimation")
        gradientLayer.add(endPointAnimation, forKey: "endPointAnimation")
    }

    
    override func draw(_ rect: CGRect) {
        
        let circlePath = UIBezierPath(ovalIn: rect.insetBy(dx: ringWidth / 2, dy: ringWidth / 2))
        backgroundLayer.path = circlePath.cgPath

        progressLayer.path = circlePath.cgPath
        progressLayer.lineCap = .round
        progressLayer.strokeStart = 0
        progressLayer.strokeEnd = progress
        progressLayer.strokeColor = UIColor.black.cgColor
        
        gradientLayer.frame = rect
        
        let ar = Double((rect.width / 2) ) - 37 / 2
        let ax = ar * cos((1.49 * Double.pi + Double.pi * Double(2)))
        let ay = ar * sin((1.49 * Double.pi + Double.pi * Double(2)))

        finishCircle.path = UIBezierPath(ovalIn: CGRect(x: ax + ar , y: ay + ar , width: 37, height: 37)).cgPath
        
        let innerCirclePath = UIBezierPath(ovalIn: circlePath.bounds.insetBy(dx: 28, dy: 28))
        innerCircle.path = innerCirclePath.cgPath
        
        let centralCirclePath = UIBezierPath(ovalIn: innerCirclePath.bounds.insetBy(dx: 10, dy: 10))
        centralCircle.path = centralCirclePath.cgPath
        
        
        
        let r = Double((rect.width / 2) ) - (knobDiameter / 2 - 1)
        let x = r * cos((2 * Double.pi + Double.pi * Double((progress - 0.25) * 2)))
        let y = r * sin((2 * Double.pi + Double.pi * Double((progress - 0.25) * 2)))
        
        let knobPath = UIBezierPath(ovalIn: CGRect(x: x + r, y: y + r, width: knobDiameter, height: knobDiameter))
        knob.path = knobPath.cgPath
        
        if progress == 1 {
            knob.fillColor = UIColor.orange.cgColor
        }
      
        bringSubviewToFront(subviews[0])
    }
}
