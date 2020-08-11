# SendSmsJbang

A [JBang](https://jbang.dev) script for sending SMS with Twilio.

  - Create a Twilio account https://twilio.com/try-twilio
  - Set `TWILIO_ACCOUNT_SID` and `TWILIO_AUTH_TOKEN` environment variables
  - Run the script with `jbang https://github.com/mjg123/SendSmsJbang/blob/master/SendSms.java`
  - Follow the instructions for jbang to trust that script
  - Run it again, set the `--to` number and `--from` number.  The message can be provided at the end of the argument list, or by piping from `stdin`
  

Like this:

```
fortune | jbang https://github.com/mjg123/SendSmsJbang/blob/master/SendSms.java --to '<YOUR_CELL_NUMBER>' --from '<YOUR_TWILIO_NUMBER>'
```
