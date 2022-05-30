package view;

import java.util.Queue;

import controller.Controller;
import model.AnimationInstruction;

public class AnimationThread implements Runnable {

	private Queue<AnimationInstruction> animationQueue;
	private MainWindow window;
	private Controller controller;

	AnimationThread(Queue<AnimationInstruction> animationQueue, MainWindow window, Controller controller) {
		this.animationQueue = animationQueue;
		this.window = window;
		this.controller = controller;
	}

	@Override
	public void run() {

		while (!animationQueue.isEmpty()) {
			AnimationInstruction currentAnimationStep = animationQueue.poll();

			window.drawGridCells(currentAnimationStep.getCellsToRedraw());
			window.displayMessage(currentAnimationStep.getMessage());

			if(controller.accessStepSize(-1) != 0) {
				synchronized (this) {
					try {
						this.wait(controller.accessStepSize(-1));
					} catch (InterruptedException e) {
						System.err.println("Error while trying to stop the animation Thread");
						e.printStackTrace();
					}
				}
			}
			
		}
		window.animationEnded();
	}
}
