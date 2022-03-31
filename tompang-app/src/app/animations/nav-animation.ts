
import { AnimationController, Animation } from '@ionic/angular';

export const enterAnimation = (baseEl: HTMLElement, opts?: any): Animation => {
  const animationCtrl = new AnimationController();
  return animationCtrl.create().addElement(opts.enteringEl).duration(0); //No animations
};
