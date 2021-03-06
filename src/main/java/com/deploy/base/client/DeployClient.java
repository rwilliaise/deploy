package com.deploy.base.client;

import com.deploy.base.DeployMod;
import com.deploy.base.client.screen.ControllerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

public class DeployClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(DeployMod.CONTROLLER_SCREEN_HANDLER, ControllerScreen::new);

		BlockRenderLayerMap.INSTANCE.putBlock(DeployMod.CABLE, RenderLayer.getTranslucent());
	}

}
