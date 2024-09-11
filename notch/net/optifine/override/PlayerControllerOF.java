package notch.net.optifine.override;

import bqq;
import bqr;
import bsr;
import cmx;
import ewy;
import ewz;
import fgo;
import fzg;
import fzo;
import geb;
import jd;
import ji;

public class PlayerControllerOF extends fzo {
  private boolean acting = false;
  
  private jd lastClickBlockPos = null;
  
  private bsr lastClickEntity = null;
  
  public PlayerControllerOF(fgo mcIn, fzg netHandler) {
    super(mcIn, netHandler);
  }
  
  public boolean a(jd loc, ji face) {
    this.acting = true;
    this.lastClickBlockPos = loc;
    boolean res = super.a(loc, face);
    this.acting = false;
    return res;
  }
  
  public boolean b(jd posBlock, ji directionFacing) {
    this.acting = true;
    this.lastClickBlockPos = posBlock;
    boolean res = super.b(posBlock, directionFacing);
    this.acting = false;
    return res;
  }
  
  public bqr a(cmx player, bqq hand) {
    this.acting = true;
    bqr res = super.a(player, hand);
    this.acting = false;
    return res;
  }
  
  public bqr a(geb player, bqq hand, ewy rayTrace) {
    this.acting = true;
    this.lastClickBlockPos = rayTrace.a();
    bqr res = super.a(player, hand, rayTrace);
    this.acting = false;
    return res;
  }
  
  public bqr a(cmx player, bsr target, bqq hand) {
    this.lastClickEntity = target;
    return super.a(player, target, hand);
  }
  
  public bqr a(cmx player, bsr target, ewz ray, bqq hand) {
    this.lastClickEntity = target;
    return super.a(player, target, ray, hand);
  }
  
  public boolean isActing() {
    return this.acting;
  }
  
  public jd getLastClickBlockPos() {
    return this.lastClickBlockPos;
  }
  
  public bsr getLastClickEntity() {
    return this.lastClickEntity;
  }
}
