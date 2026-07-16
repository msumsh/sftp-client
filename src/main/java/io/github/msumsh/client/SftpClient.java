package io.github.msumsh.client;

import com.jcraft.jsch.*;

import java.nio.file.Path;
import java.util.Properties;

public class SftpClient {
    private final String host;
    private final int port;
    private final String login;
    private final String password;

    private Session session;
    private ChannelSftp channel;

    public SftpClient(String host, int port, String login, String password) {
        this.host = host;
        this.port = port;
        this.login = login;
        this.password = password;

        this.session = null;
        this.channel = null;
    }

    public void connect() throws JSchException {
        JSch jsch = new JSch();

        this.session = jsch.getSession(login, host, port);

        session.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();

        this.channel = (ChannelSftp) session.openChannel("sftp");

        channel.connect();
    }

    public void download(String serverFile, Path localFile) throws SftpException {
        if (channel != null && channel.isConnected()) {
            this.channel.get(serverFile, localFile.toString());
        }
    }

    public void upload(Path localFile, String serverFile) throws SftpException {
        if (channel != null && channel.isConnected()) {
            this.channel.put(localFile.toString(), serverFile);
        }
    }

    public void disconnect() {
        if (channel != null && channel.isConnected()) {
            channel.disconnect();

            channel = null;
        }

        if (session != null && session.isConnected()) {
            session.disconnect();

            session = null;
        }
    }

}
