/*
 * MIT License
 *
 * Copyright (c) 2022, Apptastic Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.apptasticsoftware.rssreader;

import java.util.Objects;

/**
 * Configuration class for HTTP proxy settings.
 * Supports HTTP/HTTPS proxy with optional authentication.
 */
public class ProxyConfig {
    private final String host;
    private final int port;
    private String username;
    private String password;

    /**
     * Constructor
     * @param host proxy host address
     * @param port proxy port number
     */
    public ProxyConfig(String host, int port) {
        Objects.requireNonNull(host, "Proxy host must not be null");
        if (host.isBlank()) {
            throw new IllegalArgumentException("Proxy host must not be blank");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Proxy port must be between 1 and 65535");
        }
        this.host = host;
        this.port = port;
    }

    /**
     * Get the proxy host address.
     * @return proxy host
     */
    public String getHost() {
        return host;
    }

    /**
     * Get the proxy port number.
     * @return proxy port
     */
    public int getPort() {
        return port;
    }

    /**
     * Get the username for proxy authentication.
     * @return username or null if not set
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username for proxy authentication.
     * @param username username
     * @return this ProxyConfig instance
     */
    public ProxyConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Get the password for proxy authentication.
     * @return password or null if not set
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password for proxy authentication.
     * @param password password
     * @return this ProxyConfig instance
     */
    public ProxyConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Check if proxy authentication credentials are set.
     * @return true if both username and password are set
     */
    public boolean hasCredentials() {
        return username != null && !username.isBlank() && password != null;
    }
}
