package notch.net.optifine.entity.model;

import bsx;
import dqj;
import fwg;
import fyk;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.optifine.Config;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelAdapterAllay;
import net.optifine.entity.model.ModelAdapterArmorStand;
import net.optifine.entity.model.ModelAdapterArrow;
import net.optifine.entity.model.ModelAdapterAxolotl;
import net.optifine.entity.model.ModelAdapterBanner;
import net.optifine.entity.model.ModelAdapterBat;
import net.optifine.entity.model.ModelAdapterBed;
import net.optifine.entity.model.ModelAdapterBee;
import net.optifine.entity.model.ModelAdapterBell;
import net.optifine.entity.model.ModelAdapterBlaze;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelAdapterBook;
import net.optifine.entity.model.ModelAdapterBookLectern;
import net.optifine.entity.model.ModelAdapterBreeze;
import net.optifine.entity.model.ModelAdapterBreezeEyes;
import net.optifine.entity.model.ModelAdapterBreezeWind;
import net.optifine.entity.model.ModelAdapterCamel;
import net.optifine.entity.model.ModelAdapterCat;
import net.optifine.entity.model.ModelAdapterCatCollar;
import net.optifine.entity.model.ModelAdapterCaveSpider;
import net.optifine.entity.model.ModelAdapterChest;
import net.optifine.entity.model.ModelAdapterChestBoat;
import net.optifine.entity.model.ModelAdapterChestLarge;
import net.optifine.entity.model.ModelAdapterChestRaft;
import net.optifine.entity.model.ModelAdapterChicken;
import net.optifine.entity.model.ModelAdapterCod;
import net.optifine.entity.model.ModelAdapterConduit;
import net.optifine.entity.model.ModelAdapterCow;
import net.optifine.entity.model.ModelAdapterCreeper;
import net.optifine.entity.model.ModelAdapterCreeperCharge;
import net.optifine.entity.model.ModelAdapterDecoratedPot;
import net.optifine.entity.model.ModelAdapterDolphin;
import net.optifine.entity.model.ModelAdapterDonkey;
import net.optifine.entity.model.ModelAdapterDragon;
import net.optifine.entity.model.ModelAdapterDrowned;
import net.optifine.entity.model.ModelAdapterDrownedOuter;
import net.optifine.entity.model.ModelAdapterElderGuardian;
import net.optifine.entity.model.ModelAdapterEnderChest;
import net.optifine.entity.model.ModelAdapterEnderCrystal;
import net.optifine.entity.model.ModelAdapterEnderman;
import net.optifine.entity.model.ModelAdapterEndermite;
import net.optifine.entity.model.ModelAdapterEvoker;
import net.optifine.entity.model.ModelAdapterEvokerFangs;
import net.optifine.entity.model.ModelAdapterFox;
import net.optifine.entity.model.ModelAdapterFrog;
import net.optifine.entity.model.ModelAdapterGhast;
import net.optifine.entity.model.ModelAdapterGiant;
import net.optifine.entity.model.ModelAdapterGlowSquid;
import net.optifine.entity.model.ModelAdapterGoat;
import net.optifine.entity.model.ModelAdapterGuardian;
import net.optifine.entity.model.ModelAdapterHangingSign;
import net.optifine.entity.model.ModelAdapterHeadCreeper;
import net.optifine.entity.model.ModelAdapterHeadDragon;
import net.optifine.entity.model.ModelAdapterHeadPiglin;
import net.optifine.entity.model.ModelAdapterHeadPlayer;
import net.optifine.entity.model.ModelAdapterHeadSkeleton;
import net.optifine.entity.model.ModelAdapterHeadWitherSkeleton;
import net.optifine.entity.model.ModelAdapterHeadZombie;
import net.optifine.entity.model.ModelAdapterHoglin;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.ModelAdapterHorseArmor;
import net.optifine.entity.model.ModelAdapterHusk;
import net.optifine.entity.model.ModelAdapterIllusioner;
import net.optifine.entity.model.ModelAdapterIronGolem;
import net.optifine.entity.model.ModelAdapterLeadKnot;
import net.optifine.entity.model.ModelAdapterLlama;
import net.optifine.entity.model.ModelAdapterLlamaDecor;
import net.optifine.entity.model.ModelAdapterLlamaSpit;
import net.optifine.entity.model.ModelAdapterMagmaCube;
import net.optifine.entity.model.ModelAdapterMinecart;
import net.optifine.entity.model.ModelAdapterMinecartChest;
import net.optifine.entity.model.ModelAdapterMinecartCommandBlock;
import net.optifine.entity.model.ModelAdapterMinecartFurnace;
import net.optifine.entity.model.ModelAdapterMinecartHopper;
import net.optifine.entity.model.ModelAdapterMinecartMobSpawner;
import net.optifine.entity.model.ModelAdapterMinecartTnt;
import net.optifine.entity.model.ModelAdapterMooshroom;
import net.optifine.entity.model.ModelAdapterMule;
import net.optifine.entity.model.ModelAdapterOcelot;
import net.optifine.entity.model.ModelAdapterPanda;
import net.optifine.entity.model.ModelAdapterParrot;
import net.optifine.entity.model.ModelAdapterPhantom;
import net.optifine.entity.model.ModelAdapterPig;
import net.optifine.entity.model.ModelAdapterPigSaddle;
import net.optifine.entity.model.ModelAdapterPiglin;
import net.optifine.entity.model.ModelAdapterPiglinBrute;
import net.optifine.entity.model.ModelAdapterPillager;
import net.optifine.entity.model.ModelAdapterPolarBear;
import net.optifine.entity.model.ModelAdapterPufferFishBig;
import net.optifine.entity.model.ModelAdapterPufferFishMedium;
import net.optifine.entity.model.ModelAdapterPufferFishSmall;
import net.optifine.entity.model.ModelAdapterRabbit;
import net.optifine.entity.model.ModelAdapterRaft;
import net.optifine.entity.model.ModelAdapterRavager;
import net.optifine.entity.model.ModelAdapterSalmon;
import net.optifine.entity.model.ModelAdapterSheep;
import net.optifine.entity.model.ModelAdapterSheepWool;
import net.optifine.entity.model.ModelAdapterShield;
import net.optifine.entity.model.ModelAdapterShulker;
import net.optifine.entity.model.ModelAdapterShulkerBox;
import net.optifine.entity.model.ModelAdapterShulkerBullet;
import net.optifine.entity.model.ModelAdapterSign;
import net.optifine.entity.model.ModelAdapterSilverfish;
import net.optifine.entity.model.ModelAdapterSkeleton;
import net.optifine.entity.model.ModelAdapterSkeletonHorse;
import net.optifine.entity.model.ModelAdapterSlime;
import net.optifine.entity.model.ModelAdapterSlimeOuter;
import net.optifine.entity.model.ModelAdapterSniffer;
import net.optifine.entity.model.ModelAdapterSnowman;
import net.optifine.entity.model.ModelAdapterSpectralArrow;
import net.optifine.entity.model.ModelAdapterSpider;
import net.optifine.entity.model.ModelAdapterSquid;
import net.optifine.entity.model.ModelAdapterStray;
import net.optifine.entity.model.ModelAdapterStrayOuter;
import net.optifine.entity.model.ModelAdapterStrider;
import net.optifine.entity.model.ModelAdapterStriderSaddle;
import net.optifine.entity.model.ModelAdapterTadpole;
import net.optifine.entity.model.ModelAdapterTraderLlama;
import net.optifine.entity.model.ModelAdapterTraderLlamaDecor;
import net.optifine.entity.model.ModelAdapterTrappedChest;
import net.optifine.entity.model.ModelAdapterTrappedChestLarge;
import net.optifine.entity.model.ModelAdapterTrident;
import net.optifine.entity.model.ModelAdapterTropicalFishA;
import net.optifine.entity.model.ModelAdapterTropicalFishB;
import net.optifine.entity.model.ModelAdapterTropicalFishPatternA;
import net.optifine.entity.model.ModelAdapterTropicalFishPatternB;
import net.optifine.entity.model.ModelAdapterTurtle;
import net.optifine.entity.model.ModelAdapterVex;
import net.optifine.entity.model.ModelAdapterVillager;
import net.optifine.entity.model.ModelAdapterVindicator;
import net.optifine.entity.model.ModelAdapterWanderingTrader;
import net.optifine.entity.model.ModelAdapterWarden;
import net.optifine.entity.model.ModelAdapterWindCharge;
import net.optifine.entity.model.ModelAdapterWitch;
import net.optifine.entity.model.ModelAdapterWither;
import net.optifine.entity.model.ModelAdapterWitherArmor;
import net.optifine.entity.model.ModelAdapterWitherSkeleton;
import net.optifine.entity.model.ModelAdapterWitherSkull;
import net.optifine.entity.model.ModelAdapterWolf;
import net.optifine.entity.model.ModelAdapterWolfCollar;
import net.optifine.entity.model.ModelAdapterZoglin;
import net.optifine.entity.model.ModelAdapterZombie;
import net.optifine.entity.model.ModelAdapterZombieHorse;
import net.optifine.entity.model.ModelAdapterZombieVillager;
import net.optifine.entity.model.ModelAdapterZombifiedPiglin;
import net.optifine.util.Either;

public class CustomModelRegistry {
  private static Map<String, ModelAdapter> mapModelAdapters = makeMapModelAdapters();
  
  private static Map<String, ModelAdapter> makeMapModelAdapters() {
    Map<String, ModelAdapter> map = new LinkedHashMap<>();
    addModelAdapter(map, (ModelAdapter)new ModelAdapterArrow());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterArmorStand());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterAxolotl());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterAllay());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBat());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBee());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBlaze());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBoat());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBreeze());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterCamel());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterCat());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterCaveSpider());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterChestBoat());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterChicken());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterCod());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterCow());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterCreeper());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterDragon());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterDonkey());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterDolphin());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterDrowned());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterElderGuardian());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterEnderCrystal());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterEnderman());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterEndermite());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterEvoker());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterEvokerFangs());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterFox());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterFrog());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterGhast());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterGiant());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterGlowSquid());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterGoat());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterGuardian());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHoglin());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHorse());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHusk());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterIllusioner());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterIronGolem());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterLeadKnot());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterLlama());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterLlamaSpit());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMagmaCube());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMinecart());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMinecartChest());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMinecartCommandBlock());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMinecartFurnace());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMinecartHopper());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMinecartTnt());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMinecartMobSpawner());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMooshroom());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterMule());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterOcelot());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPanda());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterParrot());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPhantom());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPig());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPiglin());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPiglinBrute());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPolarBear());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPillager());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPufferFishBig());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPufferFishMedium());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPufferFishSmall());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterRabbit());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterRavager());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSalmon());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSheep());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterShulker());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterShulkerBullet());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSilverfish());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSkeleton());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSkeletonHorse());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSlime());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSnowman());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSniffer());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSpectralArrow());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSpider());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSquid());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterStray());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterStrider());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTadpole());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTraderLlama());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTrident());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTropicalFishA());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTropicalFishB());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTurtle());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterVex());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterVillager());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterVindicator());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWanderingTrader());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWarden());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWindCharge());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWitch());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWither());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWitherSkeleton());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWitherSkull());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWolf());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterZoglin());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterZombie());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterZombieHorse());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterZombieVillager());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterZombifiedPiglin());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterRaft());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterChestRaft());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBreezeEyes());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBreezeWind());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterCatCollar());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterCreeperCharge());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterDrownedOuter());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHorseArmor());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterLlamaDecor());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterPigSaddle());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSheepWool());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSlimeOuter());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterStrayOuter());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterStriderSaddle());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTraderLlamaDecor());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTropicalFishPatternA());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTropicalFishPatternB());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWitherArmor());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterWolfCollar());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBanner());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBed());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBell());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBook());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterBookLectern());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterChest());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterChestLarge());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterConduit());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterDecoratedPot());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterEnderChest());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHangingSign());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHeadCreeper());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHeadDragon());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHeadPiglin());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHeadPlayer());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHeadSkeleton());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHeadWitherSkeleton());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterHeadZombie());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterShulkerBox());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterSign());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTrappedChest());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterTrappedChestLarge());
    addModelAdapter(map, (ModelAdapter)new ModelAdapterShield());
    return map;
  }
  
  private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter) {
    addModelAdapter(map, modelAdapter, modelAdapter.getName());
    String[] aliases = modelAdapter.getAliases();
    if (aliases != null)
      for (int j = 0; j < aliases.length; j++) {
        String alias = aliases[j];
        addModelAdapter(map, modelAdapter, alias);
      }  
    fwg model = modelAdapter.makeModel();
    String[] names = modelAdapter.getModelRendererNames();
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      fyk mr = modelAdapter.getModelRenderer(model, name);
      if (mr == null)
        Config.warn("Model renderer not found, model: " + modelAdapter.getName() + ", name: " + name); 
    } 
  }
  
  private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter, String name) {
    if (map.containsKey(name)) {
      String typeStr = "?";
      Either<bsx, dqj> type = modelAdapter.getType();
      if (type == null) {
        typeStr = "";
      } else if (type.getLeft().isPresent()) {
        typeStr = ((bsx)type.getLeft().get()).g();
      } else if (type.getRight().isPresent()) {
        typeStr = String.valueOf(dqj.a(type.getRight().get()));
      } 
      Config.warn("Model adapter already registered for id: " + name + ", type: " + typeStr);
    } 
    map.put(name, modelAdapter);
  }
  
  public static ModelAdapter getModelAdapter(String name) {
    return mapModelAdapters.get(name);
  }
  
  public static String[] getModelNames() {
    Set<String> setNames = mapModelAdapters.keySet();
    String[] names = setNames.<String>toArray(new String[setNames.size()]);
    return names;
  }
}