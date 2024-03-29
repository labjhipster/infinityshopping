import * as dayjs from 'dayjs';
import { PaymentStatusEnum } from '../enumerations/payment-status-enum.model';

export interface IPayment {
  id?: number;
  name?: string;
  priceNet?: number;
  vat?: number;
  priceGross?: number | null;
  paymentStatus?: PaymentStatusEnum | null;
  createTime?: dayjs.Dayjs | null;
  updateTime?: dayjs.Dayjs | null;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public name?: string,
    public priceNet?: number,
    public vat?: number,
    public priceGross?: number | null,
    public paymentStatus?: PaymentStatusEnum | null,
    public createTime?: dayjs.Dayjs | null,
    public updateTime?: dayjs.Dayjs | null
  ) {}
}

export function getPaymentIdentifier(payment: IPayment): number | undefined {
  return payment.id;
}
