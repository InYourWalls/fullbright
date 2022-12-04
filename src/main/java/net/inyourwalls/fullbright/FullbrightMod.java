// A simple fullbright mod.
package net.inyourwalls.fullbright;

// Imports:
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.inyourwalls.fullbright.mixin.ForceSetAccessor;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FullbrightMod implements ClientModInitializer {
	// Get a logger.
	public static final Logger LOGGER = LoggerFactory.getLogger(FullbrightMod.class);

	// The previous gamma value, and whether fullbright is enabled.
	private static boolean enabled = false;
	private static double previousGamma;

	// Register the keybinding.
	private static final KeyMapping FULLBRIGHT_KEY = KeyBindingHelper.registerKeyBinding(new KeyMapping(
			"fullbright.key",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_PAGE_DOWN,
			"fullbright.category"
	));

	@Override
	public void onInitializeClient(ModContainer mod) {
		ClientTickEvents.END.register(client -> {
			if (FULLBRIGHT_KEY.isDown()) {
				if (enabled) {
					// Restore gamma.
					client.options.gamma().set(previousGamma);
					enabled = false;
				} else {
					// Forcibly set the gamma beyond the game limits, making it very bright.
					previousGamma = client.options.gamma().get();
					((ForceSetAccessor)(Object) client.options.gamma()).forceSet(100.0);
					enabled = true;
				}
			}
		});

		LOGGER.info("{} v{} initialised successfully.", mod.metadata().name(), mod.metadata().version());
	}
}
