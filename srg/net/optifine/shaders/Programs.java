package srg.net.optifine.shaders;

import java.util.ArrayList;
import java.util.List;
import net.optifine.shaders.Program;
import net.optifine.shaders.ProgramStage;

public class Programs {
  private List<Program> programs = new ArrayList<>();
  
  private Program programNone = make("", ProgramStage.NONE, true);
  
  public Program make(String name, ProgramStage programStage, Program backupProgram) {
    int index = this.programs.size();
    Program prog = new Program(index, name, programStage, backupProgram);
    this.programs.add(prog);
    return prog;
  }
  
  private Program make(String name, ProgramStage programStage, boolean ownBackup) {
    int index = this.programs.size();
    Program prog = new Program(index, name, programStage, ownBackup);
    this.programs.add(prog);
    return prog;
  }
  
  public Program makeGbuffers(String name, Program backupProgram) {
    return make(name, ProgramStage.GBUFFERS, backupProgram);
  }
  
  public Program makeComposite(String name) {
    return make(name, ProgramStage.COMPOSITE, this.programNone);
  }
  
  public Program makeDeferred(String name) {
    return make(name, ProgramStage.DEFERRED, this.programNone);
  }
  
  public Program makeShadow(String name, Program backupProgram) {
    return make(name, ProgramStage.SHADOW, backupProgram);
  }
  
  public Program makeVirtual(String name) {
    return make(name, ProgramStage.NONE, true);
  }
  
  public Program[] makePrograms(String prefix, int count, ProgramStage stage, Program backupProgram) {
    Program[] ps = new Program[count];
    for (int i = 0; i < count; i++) {
      String name = (i == 0) ? prefix : (prefix + prefix);
      ps[i] = make(name, stage, this.programNone);
    } 
    return ps;
  }
  
  public Program[] makeComposites(String prefix, int count) {
    return makePrograms(prefix, count, ProgramStage.COMPOSITE, this.programNone);
  }
  
  public Program[] makeShadowcomps(String prefix, int count) {
    return makePrograms(prefix, count, ProgramStage.SHADOWCOMP, this.programNone);
  }
  
  public Program[] makePrepares(String prefix, int count) {
    return makePrograms(prefix, count, ProgramStage.PREPARE, this.programNone);
  }
  
  public Program[] makeDeferreds(String prefix, int count) {
    return makePrograms(prefix, count, ProgramStage.DEFERRED, this.programNone);
  }
  
  public Program getProgramNone() {
    return this.programNone;
  }
  
  public int getCount() {
    return this.programs.size();
  }
  
  public Program getProgram(String name) {
    if (name == null)
      return null; 
    for (int i = 0; i < this.programs.size(); i++) {
      Program p = this.programs.get(i);
      String progName = p.getName();
      if (progName.equals(name))
        return p; 
    } 
    return null;
  }
  
  public String[] getProgramNames() {
    String[] names = new String[this.programs.size()];
    for (int i = 0; i < names.length; i++)
      names[i] = ((Program)this.programs.get(i)).getName(); 
    return names;
  }
  
  public Program[] getPrograms() {
    Program[] arr = this.programs.<Program>toArray(new Program[this.programs.size()]);
    return arr;
  }
  
  public Program[] getPrograms(Program programFrom, Program programTo) {
    int iFrom = programFrom.getIndex();
    int iTo = programTo.getIndex();
    if (iFrom > iTo) {
      int j = iFrom;
      iFrom = iTo;
      iTo = j;
    } 
    Program[] progs = new Program[iTo - iFrom + 1];
    for (int i = 0; i < progs.length; i++)
      progs[i] = this.programs.get(iFrom + i); 
    return progs;
  }
  
  public String toString() {
    return this.programs.toString();
  }
}
