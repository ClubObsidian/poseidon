package org.pf4j.inject;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class PluginModule implements Module {

    private PluginWrapper wrapper;
    private Plugin plugin;

    public PluginModule(PluginWrapper wrapper, Plugin plugin) {
        this.wrapper = wrapper;
        this.plugin = plugin;
    }


    @Override
    public void configure(Binder binder) {
        binder.bind(PluginWrapper.class).toInstance(this.wrapper);
    }

    public void inject() {
        Guice.createInjector(this).injectMembers(this.plugin);
    }
}
