//usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 11+
//DEPS info.picocli:picocli:4.2.0
//DEPS com.twilio.sdk:twilio:7.54.2

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Scanner;
import java.util.concurrent.Callable;

@Command(name = "SendSms", mixinStandardHelpOptions = true, version = "SendSms 0.1",
    description = "Send an SMS from the command line with Java")
class SendSms implements Callable<Integer> {

    @CommandLine.Option(
        names = {"-t", "--to"},
        description = "The number you're sending the message @|bold to|@",
        required = true)
    private String toPhoneNumber;

    @CommandLine.Option(
        names = {"-f", "--from"},
        description = "The number you're sending the message @|bold from|@",
        required = true)
    private String fromPhoneNumber;

    @Parameters(
        index = "0",
        description = "The message to send",
        arity = "0..*"
    )
    private String[] greeting;

    public static void main(String... args) {
        int exitCode = new CommandLine(new SendSms()).execute(args);
        System.exit(exitCode);
    }

    private void printlnAnsi(String msg) {
        System.out.println(CommandLine.Help.Ansi.AUTO.string(msg));
    }

    @Override
    public Integer call() {

        String wholeMessage;

        if (greeting != null) {
            wholeMessage = String.join(" ", greeting);

        } else {
            var scanner = new Scanner(System.in).useDelimiter("\\A");
            wholeMessage = "";
            while (scanner.hasNext()) {
                wholeMessage += scanner.next();
            }
        }

        if (wholeMessage.isBlank()){
            printlnAnsi("@|red You need to provide a message somehow|@");
            return 1;
        }

        try {
            System.out.print("Sending to ..." + toPhoneNumber + ": ");
            sendSMS(toPhoneNumber, fromPhoneNumber, wholeMessage);
            printlnAnsi("@|green OK|@");

        } catch (Exception e) {
            printlnAnsi("@|red FAILED|@");
            printlnAnsi("@|red " + e.getMessage() + "|@");
            return 1;
        }

        return 0;
    }

    private void sendSMS(String to, String from, String wholeMessage) {

        Twilio.init(
            System.getenv("TWILIO_ACCOUNT_SID"),
            System.getenv("TWILIO_AUTH_TOKEN"));

        Message
            .creator(new PhoneNumber(to), new PhoneNumber(from), wholeMessage)
            .create();

    }
}
