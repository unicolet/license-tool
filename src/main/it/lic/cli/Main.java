package it.lic.cli;

import it.lic.License;
import it.lic.Wallet;
import it.lic.keypair.LicenseKeyPair;
import it.lic.storage.FileStorage;
import it.lic.storage.System;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 * License tool cli entrypoint.
 * @author Umberto Nicoletti (umberto.nicoletti@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class Main {
    final Options options;

    private Main(String[] args) throws Exception {
        this.options = new Options();
        this.options.addOption("h", false, "help");
        this.options.addOption("l", true, "show named keypair");
        this.options.addOption("L", true, "list licenses of named keypair");
        this.options.addOption("k", true, "new named keypair");
        this.options.addOption("K", true, "new license for named keypair");
        final CommandLineParser parser = new DefaultParser();
        final CommandLine cli = parser.parse(this.options, args);
        this.run(cli);
    }

    public static void main(String[] args) throws Exception {
        Main program = new Main(args);
    }

    private void run(final CommandLine cli) throws Exception {
        final Wallet wallet = new Wallet.Default(
            new FileStorage(
                new System.Default()
            )
        );
        if(cli.hasOption("h")) {
            this.help();
        } else if(cli.hasOption("l")) {
            final LicenseKeyPair lkp = wallet.licenseKeyPair(cli.getOptionValue("l"));
            java.lang.System.out.println(
                String.format(
                    "[%s] Public key: %s",
                    lkp.name(),
                    new String(Base64.getEncoder().encode(lkp.publicKey().getEncoded()))
                )
            );
        } else if(cli.hasOption("L")) {
            final Iterator<License> licenses = wallet.licenses(
                wallet.licenseKeyPair(cli.getOptionValue("L")),
                ""
            );
            while(licenses.hasNext()) {
                License current = licenses.next();
                java.lang.System.out.println(
                    String.format(
                        "%s\t%s\t%s\t%s",
                        current.name(),
                        current.issuer(),
                        current.until(),
                        current.encode()
                    )
                );
            }
        } else if(cli.hasOption("k")) {
            final LicenseKeyPair lkp = wallet.newLicenseKeyPair(cli.getOptionValue("k"));
            java.lang.System.out.println(
                String.format(
                    "new keypair created: %s",
                    new String(Base64.getEncoder().encode(lkp.publicKey().getEncoded()))
                )
            );
        } else if(cli.hasOption("K")) {
            final Calendar expire = Calendar.getInstance();
            expire.add(Calendar.YEAR, 1);
            Optional<License> license = wallet.hasLicenseFor(wallet.licenseKeyPair("prometeo"), cli.getOptionValue("K"));
            if(license.isPresent()) {
                java.lang.System.out.println(
                    String.format(
                        "license already esists: %s",
                        license.get().encode()
                    )
                );
            } else {
                final License newlicense = wallet.newLicense(
                    cli.getOptionValue("K"),
                    wallet.licenseKeyPair("prometeo"),
                    cli.getOptionValue("K"),
                    expire.getTime(),
                    Collections.emptyMap()
                );
                java.lang.System.out.println(
                    String.format(
                        "new license created: %s",
                        newlicense.encode()
                    )
                );
            }
        }
    }

    private void help() {
        final HelpFormatter help = new HelpFormatter();
        help.printHelp("license-tool", this.options);
    }
}
