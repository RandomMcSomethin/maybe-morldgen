package net.fabricmc.example.mixin;

import net.minecraft.util.SignType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SignType.class)
public interface SignTypeInvoker {
    @Invoker("register")
    public static SignType registerSignType(SignType type) {
        throw new AssertionError();
    }

    @Invoker("<init>")
    public static SignType init(String name) {
        throw new AssertionError();
    }
}
