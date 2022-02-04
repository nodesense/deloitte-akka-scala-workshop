package workshop.models


// Domain object, Database, data model, ML/AI
case class Invoice(id:  Int, customerId: Int, amount: Double)

// final case class Invoices(invoices: Seq[Invoice])

// REST APIs compatible, create models for HTTP Request and HTTP Response
// Requests
// single instance
case object GetAllInvoices
// specific instances
case class GetInvoice(id: Int)
case class CreateInvoice(invoice: Invoice)
case class UpdateInvoice(invoice: Invoice)
case class DeleteInvoice(id: Int)

// response
// case class GetAllInvoicesResponse(invoices: Invoices)
case class GetAllInvoicesResponse(invoices: Seq[Invoice])

case class GetInvoiceResponse(invoice: Invoice)