import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import * as solicitacoesApi from "../api/solicitacoes";
import { SolicitacaoDetalhePage } from "./SolicitacaoDetalhePage";

vi.mock("../api/solicitacoes");

function renderPage(id = "s1") {
  return render(
    <MemoryRouter initialEntries={[`/solicitacoes/${id}`]}>
      <Routes>
        <Route path="/solicitacoes/:id" element={<SolicitacaoDetalhePage />} />
      </Routes>
    </MemoryRouter>,
  );
}

describe("SolicitacaoDetalhePage", () => {
  beforeEach(() => {
    vi.resetAllMocks();
    vi.mocked(solicitacoesApi.buscarSolicitacao).mockResolvedValue({
      id: "s1",
      cidadaoId: "c1",
      titulo: "Poste queimado",
      descricao: "Sem luz",
      bairro: "Centro",
      categoria: "ILUMINACAO",
      status: "ABERTA",
    });
    vi.mocked(solicitacoesApi.listarHistorico).mockResolvedValue([
      {
        status: "ABERTA",
        data: "2026-01-01T12:00:00.000Z",
        observacao: "Solicitação criada",
      },
      {
        status: "EM_ANDAMENTO",
        data: "2026-01-02T15:00:00.000Z",
        observacao: "Em vistoria",
      },
    ]);
  });

  it("exibe itens do histórico retornados pela API", async () => {
    renderPage();

    expect(await screen.findByText("Poste queimado")).toBeInTheDocument();
    expect(screen.getByText(/histórico de status/i)).toBeInTheDocument();
    expect(screen.getByText("Solicitação criada")).toBeInTheDocument();
    expect(screen.getByText("Em vistoria")).toBeInTheDocument();
    const historico = screen.getByRole("list");
    expect(historico).toHaveTextContent("EM_ANDAMENTO");
  });
});
