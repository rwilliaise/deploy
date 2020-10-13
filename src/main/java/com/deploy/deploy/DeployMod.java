package com.deploy.deploy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DeployMod.MOD_ID)
public class DeployMod
{
	public static final String MOD_ID = "deploy";

	public DeployMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		DeployRegistry.init();
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
	}
}
