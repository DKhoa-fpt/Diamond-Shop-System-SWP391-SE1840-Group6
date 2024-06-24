export class OrderDTO{
    customerName: string;
    address : string;
    phoneNumber: string;
    totalAmount: number;
    orderDeatil : string;
    createAt: number;
    nameCard: string;
    cardNumber: string;
    email: string
    constructor( order = null){
        if(order !==null){
            this.customerName = order.customerName;
            this.address = order.address;
            this.phoneNumber = order.phoneNumber;
            this.totalAmount = order.totalAmount;
            this.orderDeatil = order.orderDeatil;
            this.createAt = order.createAt;
            this.nameCard = order.nameCard;
            this.cardNumber = order.cardNumber;
            this.email = order.email;
        }

    }
}