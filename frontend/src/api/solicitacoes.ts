import { apiFetch } from "./client";
import type {
  HistoricoStatus,
  MudarStatusPayload,
  Solicitacao,
  SolicitacaoPayload,
} from "../types/solicitacao";

export function listarSolicitacoes(): Promise<Solicitacao[]> {
  return apiFetch<Solicitacao[]>("/solicitacoes");
}

export function criarSolicitacao(payload: SolicitacaoPayload): Promise<Solicitacao> {
  return apiFetch<Solicitacao>("/solicitacoes", {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

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
