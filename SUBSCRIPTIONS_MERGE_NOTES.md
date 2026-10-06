# Subscription integration

Ports Abdulrhman’s subscription plans, checkout and webhook code onto the supplied team snapshot. Existing garage, technician, email, authentication and frontend files are preserved. Only pom.xml and SecurityConfig.java are changed among existing files.

## Configuration
Set STRIPE_SECRET_KEY and STRIPE_WEBHOOK_SECRET in the run environment. The webhook signing secret is different from the API secret. No credentials are included in this patch. Paid checkout redirects retain the original localhost:4200 URLs and currency OMR; verify these against your frontend and Stripe account before using real payments.

Admin: /api/admin/subscription-plans (create/list/update/status).
Owner: GET /api/garage-owner/subscription-plans; POST /api/subscriptions/checkout with planId and garageId. Use the existing JWT.
Create a zero-price plan to offer the free trial. Trial begins when checkout is requested, lasts three calendar months and is limited to one use per owner. Paid plans use durationDays. Free subscription history is saved with FREE status; paid checkout starts PENDING and the signed checkout.session.completed event marks it PAID only when payment_status is paid.

## Scope and limitations
This imports the uploaded subscription feature; it does not add recurring billing or block existing garage operations when a subscription expires. Expiry access enforcement is not implemented in the uploaded feature and requires a separate integration decision. Pending paid periods retain the uploaded behavior of starting at checkout creation. Test with Stripe test credentials before merging.

## Validation
Existing-file byte checks confirm unrelated files are untouched. Patch applicability and whitespace checks passed. Build/runtime tests could not be run here: only Java 17 is installed while the project requires Java 21; Maven dependencies are unavailable. Run the project build and verify free trial, repeat-trial rejection, paid checkout and webhook with your local Java 21 setup.
