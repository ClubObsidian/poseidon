package org.pf4j;

import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationType;
import org.pf4j.util.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class YamlPluginDescriptorFinder implements PluginDescriptorFinder {

    @Override
    public boolean isApplicable(Path pluginPath) {
        return Files.exists(pluginPath) && (Files.isDirectory(pluginPath) || FileUtils.isJarFile(pluginPath));
    }

    @Override
    public PluginDescriptor find(Path pluginPath) {
        try (JarFile jar = new JarFile(pluginPath.toFile())) {
            ZipEntry entry = jar.getEntry("plugin.yml");
            if(entry != null) {
                InputStream pluginStream = jar.getInputStream(entry);
                Configuration pluginConfig = Configuration.load(pluginStream, ConfigurationType.YAML);
                String name = pluginConfig.getString("name");
                String description = "";
                String main = pluginConfig.getString("main");
                String version = pluginConfig.getString("version");
                String requires = "*";
                String provider = "";
                String license = "";
                DefaultPluginDescriptor pluginDescriptor = new DefaultPluginDescriptor(name, description, main, version, requires, provider, license);
                List<String> dependenciesList = pluginConfig.getStringList("dependencies");
                String[] dependencies = dependenciesList.toArray(new String[dependenciesList.size()]);
                for(String depend : dependencies) {
                    pluginDescriptor.addDependency(new PluginDependency(depend));
                }

                pluginStream.close();
                return pluginDescriptor;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}