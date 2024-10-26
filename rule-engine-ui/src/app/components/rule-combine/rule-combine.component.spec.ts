import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleCombineComponent } from './rule-combine.component';

describe('RuleCombineComponent', () => {
  let component: RuleCombineComponent;
  let fixture: ComponentFixture<RuleCombineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RuleCombineComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RuleCombineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
