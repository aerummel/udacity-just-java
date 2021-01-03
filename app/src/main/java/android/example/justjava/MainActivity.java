package android.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 2;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;
    String name;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field);
        name = nameField.getText().toString();

        int totalPrice = calculatePrice();
        String priceMessage = createOrderSummary(totalPrice);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        startActivity(intent);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        } else {
//            Toast.makeText(getApplicationContext(), getString(R.string.toast_email_intent), Toast.LENGTH_SHORT).show();
//            displayMessage(priceMessage);
//        }

    }

    /**
     * This method creates an order summary.
     *
     * @param price is the total price of the order.
     */
    public String createOrderSummary(int price) {
        return getString(R.string.order_summary_name, name) +
                "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream) +
                "\n" + getString(R.string.order_summary_chocolate, hasChocolate) +
                "\n" + getString(R.string.order_summary_quantity, Integer.toString(quantity)) +
                "\n" + getString(R.string.order_summary_price, Integer.toString(price)) +
                "\n" + getString(R.string.thank_you);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice() {
        // the base price of one cup of coffee is $5
        int price = 5;

        // add $1 if whipped cream is added
        if (hasWhippedCream) {
            price += 1;
        }

        //a add $2 if chocolate is added
        if (hasChocolate) {
            price += 2;
        }

        // the total price is calculated and returned
        return quantity * price;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity displayed on the screen.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity += 1;
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_increment), Toast.LENGTH_SHORT).show();
        }

        displayQuantity(quantity);
    }

    /**
     * This method decrements the quantity displayed on the screen.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity -= 1;
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_decrement), Toast.LENGTH_SHORT).show();
        }

        displayQuantity(quantity);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}