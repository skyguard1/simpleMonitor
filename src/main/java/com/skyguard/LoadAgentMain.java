package com.skyguard;

import com.skyguard.monitor.AgentMain;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * @author : xingrufei
 * create at:  2020-03-30  11:22
 * @description:
 */
public class LoadAgentMain {

    public static void main(String[] args) {

        List<VirtualMachineDescriptor> list = VirtualMachine.list();

        try {
            for (VirtualMachineDescriptor vmd : list) {
                if (!StringUtils.isEmpty(vmd.displayName()) && !vmd.displayName().contains("org.jetbrains") && !vmd.displayName().contains("LoadAgentMain")) {
                    File file = findPath();
                    String filePath = file.getAbsolutePath();
                    filePath = filePath.substring(0, filePath.lastIndexOf("/"));
                    filePath = filePath + "/simpleMonitor.jar";
                    // 传入目标 JVM pids
                    VirtualMachine vm = VirtualMachine.attach(vmd.id());
                    vm.loadAgent(filePath);
                }
            }
        }catch (Throwable t){
            t.printStackTrace();
        }


    }

    private static File findPath() {
        String classResourcePath = AgentMain.class.getName().replaceAll("\\.", "/") + ".class";

        URL resource = ClassLoader.getSystemClassLoader().getResource(classResourcePath);
        if (resource != null) {
            String urlString = resource.toString();
            int insidePathIndex = urlString.indexOf('!');
            boolean isInJar = insidePathIndex > -1;
            if (isInJar) {
                urlString = urlString.substring(urlString.indexOf("file:"), insidePathIndex);
                File agentJarFile = null;
                try {
                    agentJarFile = new File(new URL(urlString).toURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (agentJarFile.exists()) {
                    return agentJarFile.getParentFile();
                }
            } else {
                int prefixLength = "file:".length();
                String classLocation = urlString.substring(prefixLength, urlString.length() - classResourcePath.length());
                return new File(classLocation);
            }
        }

        throw new RuntimeException("Can not locate agent jar file.");
    }


}
