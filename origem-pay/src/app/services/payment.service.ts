import { environment } from "../../environments/environment";
import { Injectable, inject, signal } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Payment } from '../models/payment.model';
import { tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PaymentService {
  private http = inject(HttpClient);
  private readonly API_URL = environment.apiUrl;

  payments = signal<Payment[]>([]);

  listarTodos() {
    const token = localStorage.getItem('auth_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<Payment[]>(this.API_URL, { headers }).pipe(
      tap(dados => this.payments.set(dados))
    );
  }

  salvar(payment: Payment) {
    const token = localStorage.getItem('auth_token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post<Payment>(this.API_URL, payment, { headers });
  }

  getPayments() {
    return this.listarTodos();
  }
}
