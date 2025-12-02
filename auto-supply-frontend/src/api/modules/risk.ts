import request from '@/api/index'
import type { 
  Risk, 
  RiskAssessment, 
  RiskMitigation, 
  RiskQueryParams,
  RiskAssessmentQueryParams
} from '@/types/risk'

/**
 * Get risk list
 * @param params Query parameters
 */
export function getRisksApi(params?: RiskQueryParams) {
  return request.get<Risk[]>('/risks', { params })
}

/**
 * Get risk details
 * @param id Risk ID
 */
export function getRiskDetailsApi(id: number) {
  return request.get<Risk>(`/risks/${id}`)
}

/**
 * Create new risk
 * @param data Risk data
 */
export function createRiskApi(data: Partial<Risk>) {
  return request.post<Risk>('/risks', data)
}

/**
 * Update risk
 * @param id Risk ID
 * @param data Risk data
 */
export function updateRiskApi(id: number, data: Partial<Risk>) {
  return request.put<Risk>(`/risks/${id}`, data)
}

/**
 * Delete risk
 * @param id Risk ID
 */
export function deleteRiskApi(id: number) {
  return request.delete(`/risks/${id}`)
}

/**
 * Get risk assessments
 * @param params Query parameters
 */
export function getRiskAssessmentsApi(params?: RiskAssessmentQueryParams) {
  return request.get<RiskAssessment[]>('/risks/assessments', { params })
}

/**
 * Create risk assessment
 * @param data Assessment data
 */
export function createRiskAssessmentApi(data: Partial<RiskAssessment>) {
  return request.post<RiskAssessment>('/risks/assessments', data)
}

/**
 * Get risk mitigations
 * @param riskId Risk ID
 */
export function getRiskMitigationsApi(riskId: number) {
  return request.get<RiskMitigation[]>(`/risks/${riskId}/mitigations`)
}

/**
 * Create risk mitigation
 * @param riskId Risk ID
 * @param data Mitigation data
 */
export function createRiskMitigationApi(riskId: number, data: Partial<RiskMitigation>) {
  return request.post<RiskMitigation>(`/risks/${riskId}/mitigations`, data)
}

/**
 * Update risk mitigation
 * @param mitigationId Mitigation ID
 * @param data Mitigation data
 */
export function updateRiskMitigationApi(mitigationId: number, data: Partial<RiskMitigation>) {
  return request.put<RiskMitigation>(`/risks/mitigations/${mitigationId}`, data)
}