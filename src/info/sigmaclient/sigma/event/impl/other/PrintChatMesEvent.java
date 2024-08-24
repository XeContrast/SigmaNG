package info.sigmaclient.sigma.event.impl.other;

import info.sigmaclient.sigma.event.Event;
import net.minecraft.util.text.ITextComponent;

public class PrintChatMesEvent extends Event {
    public ITextComponent textComponent;

    public PrintChatMesEvent(ITextComponent textComponent) {
        this.textComponent = textComponent;
    }
}
