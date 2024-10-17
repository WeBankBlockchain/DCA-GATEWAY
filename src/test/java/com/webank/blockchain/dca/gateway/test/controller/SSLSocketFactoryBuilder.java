package com.webank.blockchain.dca.gateway.test.controller;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


public class SSLSocketFactoryBuilder {

    private static Certificate certificate = null;
    private static final String certPath = "./wbbc.crt";


    private static String tlsVersionValue = "TLS";



    public static SSLSocketFactory get() {
        File crtFile = new File(certPath);
        if (!crtFile.exists()) {

            System.out.println("not exsit");
            return null;
        }

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
        } catch (CertificateException ex) {
            System.out.println(ex);
            return null;
        }
        InputStream crtInputStream = null;
        try {
            crtInputStream = new FileInputStream(crtFile);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            return null;
        }
        try {
            certificate = cf.generateCertificate(crtInputStream);
        } catch (CertificateException ex) {
        } finally {
            if (crtInputStream != null) {// NOSONAR
                try {
                    crtInputStream.close();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }
        if (certificate == null) {
            return null;
        }
        SSLContext context = null;
        try {
            context = SSLContext.getInstance(tlsVersionValue);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            return null;
        }
        try {
            context.init(null, new TrustManager[]{mTrustManager}, null);
        } catch (KeyManagementException e) {
            System.out.println(e);
        }
        return context.getSocketFactory();
    }

    public static X509TrustManager mTrustManager = new X509TrustManager() {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {//NOSONAR

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            for (X509Certificate x509Certificate : x509Certificates) {
                //检查站点证书的有效性
                x509Certificate.checkValidity();
                try {
                    //用CA证实站点证书
                    x509Certificate.verify(certificate.getPublicKey());
                } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException | SignatureException e) {
                    System.out.println(e);
                }
            }
        }


    };
}

