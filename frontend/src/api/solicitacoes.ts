import { apiFetch } from "./client";
import type {
  HistoricoStatus,
  MudarStatusPayload,
  Solicitacao,
} from "../types/solicitacao";

export function buscarSolicitacao(id: string): Promise<Solicitacao> {
  return apiFetch<Solicitacao>(`/solicitacoes/${encodeURIComponent(id)}`);
}

export function listarHistorico(id: string): Promise<HistoricoStatus[]> {
  return apiFetch<HistoricoStatus[]>(
    `/solicitacoes/${encodeURIComponent(id)}/historico`,
  );
}

export function mudarStatus(
  id: string,
  payload: MudarStatusPayload,
): Promise<Solicitacao> {
  return apiFetch<Solicitacao>(
    `/solicitacoes/${encodeURIComponent(id)}/status`,
    {
      method: "PATCH",
      body: JSON.stringify(payload),
    },
  );
}
