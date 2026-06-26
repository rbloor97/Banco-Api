import { TestBed } from '@angular/core/testing';
import {provideHttpClient} from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

import { CuentaService } from './cuenta.service';


describe('CuentaService', () => {
  let service: CuentaService;
  

  beforeEach(() => {
      
    TestBed.configureTestingModule({
      providers: [
        CuentaService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(CuentaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
