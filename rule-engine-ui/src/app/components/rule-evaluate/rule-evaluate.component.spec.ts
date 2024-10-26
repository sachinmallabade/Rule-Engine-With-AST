import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleEvaluateComponent } from './rule-evaluate.component';

describe('RuleEvaluateComponent', () => {
  let component: RuleEvaluateComponent;
  let fixture: ComponentFixture<RuleEvaluateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RuleEvaluateComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RuleEvaluateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
