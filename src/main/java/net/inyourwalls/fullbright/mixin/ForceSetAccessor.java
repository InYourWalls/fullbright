// ForceSetAccessor.java: Allows forcibly setting values of options, skipping validation.
package net.inyourwalls.fullbright.mixin;

// Imports:
import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(OptionInstance.class)
public interface ForceSetAccessor {
	@Accessor("value")
	<T> void forceSet(T value);
}
