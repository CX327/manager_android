package org.com.manager.bean;


/**
 * Created by jie.hua on 2016/3/18
 */
public class Plugin {
    /**
     * 插件名称
     */
    public String pluginName;
    /**
     * 插件图标
     */
    public int icon;
    /**
     * 包名
     */
    public String packageName;
    /**
     * 类名
     */
    public String className;

    public Plugin() {
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Plugin(String pluginName, int icon, String packageName, String className) {
        this.pluginName = pluginName;
        this.icon = icon;
        this.packageName = packageName;
        this.className = className;
    }
}
