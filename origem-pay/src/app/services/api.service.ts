import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8082/api'; // Ajustado para seu ambiente local

  getDados() {
    return this.http.get<any[]>(`${this.API_URL}/payments`);
  }
}
